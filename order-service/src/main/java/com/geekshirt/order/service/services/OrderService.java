package com.geekshirt.order.service.services;

import com.geekshirt.order.service.client.CustomerServiceClient;
import com.geekshirt.order.service.client.InventoryServiceClient;
import com.geekshirt.order.service.dto.AccountDto;
import com.geekshirt.order.service.dto.Confirmation;
import com.geekshirt.order.service.dto.OrderRequest;
import com.geekshirt.order.service.dto.ShipmentOrderResponse;
import com.geekshirt.order.service.entities.Order;
import com.geekshirt.order.service.entities.OrderDetail;
import com.geekshirt.order.service.exceptions.AccountNotFoundException;
import com.geekshirt.order.service.exceptions.OrderNotFoundException;
import com.geekshirt.order.service.exceptions.PaymentNotAcceptedException;
import com.geekshirt.order.service.producer.ShippingOrderProducer;
import com.geekshirt.order.service.repositories.OrderRepository;
import com.geekshirt.order.service.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {

    Date date0 = new Date();

    @Autowired
    private CustomerServiceClient customerServiceClient;

    @Autowired
    private PaymentProcessorService paymentProcessorService;


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShippingOrderProducer shippingMessageProducer;

    @Autowired
    private InventoryServiceClient inventoryClient;

    @Autowired
    private OrderMailService orderMailService;

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Order createOrder(OrderRequest orderRequest) throws PaymentNotAcceptedException {
        OrderValidator.validateOrder(orderRequest);
        AccountDto accountDto = customerServiceClient.findAccountById(orderRequest.getAccountId())
                .orElseThrow(()->new AccountNotFoundException(ExceptionMessagesEnum.ACCOUNT_NOT_FOUND.getValue()));


        Order newOrder = initOrder(orderRequest);
        Confirmation confirmation = paymentProcessorService.processPayment(newOrder, accountDto);
        log.info("Payment Confirmation: {}", confirmation);

        String paymentStatus = confirmation.getTransactionStatus();
        newOrder.setPaymentStatus(OrderPaymentStatus.valueOf(paymentStatus));

        if (paymentStatus.equals(OrderPaymentStatus.DENIED.name())) {
            newOrder.setStatus(OrderStatus.NA);
            orderRepository.save(newOrder);
            throw new PaymentNotAcceptedException("The Payment added to your account was not accepted, please verify.");
        }
        log.info("Updating inventory : {}", orderRequest.getItems());
        inventoryClient.updateInventory(orderRequest.getItems());

        log.info("Sending Request to Shipping Service");
        shippingMessageProducer.send(newOrder.getOrderId(), accountDto);

        return orderRepository.save(newOrder);
    }

    private Order initOrder(OrderRequest orderRequest){
        Order orderObj = new Order();
        orderObj.setOrderId(UUID.randomUUID().toString());
        orderObj.setAccountId(orderRequest.getAccountId());
        orderObj.setStatus(OrderStatus.PENDING);

        List<OrderDetail> orderDetails = orderRequest.getItems().stream()
                .map(item -> OrderDetail.builder()
                        .price(item.getPrice())
                        .quantity(item.getQuantity())
                        .upc(item.getUpc())
                        .tax(item.getPrice() * item.getQuantity() * Constants.TAX_IMPORT)
                        .order(orderObj).build())
                .collect(Collectors.toList());
        orderObj.setDetails(orderDetails);
        orderObj.setTotalAmount(orderDetails.stream().mapToDouble(OrderDetail::getPrice).sum());
        orderObj.setTotalTax(orderObj.getTotalAmount() * Constants.TAX_IMPORT);
        orderObj.setTransactionDate(new Date());
        return orderObj;
    }

    @Cacheable(value = "orders")
    public List<Order> findAllOrders(){
        return orderRepository.findAll();

    }

    public Order findOrderById(String orderId){
        Optional<Order> order = Optional.ofNullable(orderRepository.findOrderByOrderId(orderId));
        return order
                .orElseThrow(()-> new OrderNotFoundException("Order Not Found"));
    }

    public Order findById(Long id){
        return orderRepository.findById(id)
                .orElseThrow(()-> new OrderNotFoundException("Order Not Found"));
    }

    @Cacheable(value= "ordersAccount", key = "#accountId")
    public List<Order> findOrdersByAccountId(String accountId){
        Optional<List<Order>> orders = Optional.ofNullable(orderRepository.findOrdersByAccountId(accountId));
        return orders.orElseThrow(()-> new OrderNotFoundException("Orders Were Not Found"));
    }

    public void updateShipmentOrder(ShipmentOrderResponse shipmentOrderResponse){
        try{
            Order order = findOrderById(shipmentOrderResponse.getOrderId());
            order.setStatus(OrderStatus.valueOf(shipmentOrderResponse.getShippingStatus()));
            orderRepository.save(order);
            //cada vez que hay un cambio en el status de envio se manda un email
            orderMailService.sendEmail(order, shipmentOrderResponse);

        }catch (OrderNotFoundException orderNotFoundException){
            log.info("The following order was not found {} with tracking id {}", shipmentOrderResponse.getOrderId(), shipmentOrderResponse.getTrackingId());

        }

        catch(Exception e){
            log.info("An error occurred while sending email:" + e.getMessage());

        }

    }
}
