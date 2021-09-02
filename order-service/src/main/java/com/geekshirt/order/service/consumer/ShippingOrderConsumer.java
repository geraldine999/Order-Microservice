package com.geekshirt.order.service.consumer;

import com.geekshirt.order.service.dto.OrderResponse;
import com.geekshirt.order.service.dto.ShipmentOrderResponse;
import com.geekshirt.order.service.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ShippingOrderConsumer {

    @Autowired
    private OrderService orderService;


    @RabbitListener(queues = "OUTBOUND_SHIPMENT_ORDER")//la aplicacion va a estar escuchando
    // esa cola y en el momento
    //de recibir un evento esa queue va a llamar a este metodo
    public void receive (final ShipmentOrderResponse in){
        log.info("[X] Received shipment information: {}", in);
        orderService.updateShipmentOrder(in);
    }


}
