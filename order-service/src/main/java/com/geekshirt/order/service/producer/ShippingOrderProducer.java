package com.geekshirt.order.service.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geekshirt.order.service.dto.AccountDto;
import com.geekshirt.order.service.dto.CustomerDto;
import com.geekshirt.order.service.dto.ShipmentOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j //logueo
@Service
public class ShippingOrderProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Qualifier(value = "outbound")
    private Queue queue;

    public void send(String orderId, AccountDto accountDto){
        ShipmentOrderRequest shipmentOrderRequest = new ShipmentOrderRequest();

        ObjectMapper objectMapper = new ObjectMapper();

        try{
            CustomerDto customer = accountDto.getCustomer();
            String shipmentReceiver = customer.getLastName()+" ,"+ customer.getFirstName();
            shipmentOrderRequest.setOrderId(orderId);
            shipmentOrderRequest.setName(shipmentReceiver);
            shipmentOrderRequest.setReceiptEmail(customer.getEmail());
            shipmentOrderRequest.setShipmentAdress(accountDto.getAddress());

            Message jsonMessage = MessageBuilder.withBody(objectMapper.writeValueAsString(shipmentOrderRequest).getBytes())
                    .andProperties(MessagePropertiesBuilder.newInstance()
                    .setContentType("application/json")
                    .build()).build();
            this.rabbitTemplate.convertAndSend("INBOUND_SHIPMENT_ORDER", jsonMessage);
        }catch(JsonProcessingException e ){
            e.printStackTrace();
        }
        log.debug("[X] SENT ´" + shipmentOrderRequest.toString() + " ´");
    }

}
