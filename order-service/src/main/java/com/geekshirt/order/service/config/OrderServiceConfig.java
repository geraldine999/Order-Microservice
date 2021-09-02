package com.geekshirt.order.service.config;

import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing// para implementar createdDate y lastUpdateDate
@PropertySource({"classpath:application.properties"})
@Getter
public class OrderServiceConfig {
    @Value("${customerservice.url}")
    private String customerServiceURL;
    @Value("${paymentservice.url}")
    private String paymentServiceURL;
    @Value("${inventoryservice.url}")
    private String inventoryServiceURL;

    @Qualifier(value = "outbound")
    @Bean
    public Queue inboundShipmentOrder(){
        return new Queue("INBOUND_SHIPMENT_ORDER", false, false, false);
    }

    //los mensajes recibidos en json deben ser convertidos...
    @Bean
    public Jackson2JsonMessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }


}
