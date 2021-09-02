package com.geekshirt.order.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentOrderRequest implements Serializable { //siempre hay que poner serializable
    //cuando viajan por otros medios
    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("receiptEmail")
    private String receiptEmail;

    @JsonProperty("shipmentAdress")
    private AddressDto shipmentAdress;


}
