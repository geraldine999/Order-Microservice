package com.geekshirt.order.service.dto;

import com.geekshirt.order.service.entities.Order;
import lombok.Data;

@Data
public class OrderDetailsResponse {

    private long id;

    private int quantity;

    private double price;

    private double tax;

    private String upc;

    private Order order;
    private Double totalAmount;
}
