package com.geekshirt.order.service.dto;


import com.geekshirt.order.service.entities.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponse {
    private String orderId;
    private String status;
    private String accountId;
    private Double totalAmount;
    private Double totalTax;
    private Double totalAmountTax;
    private Date transactionDate;

    List<OrderDetailsResponse> details;
}
