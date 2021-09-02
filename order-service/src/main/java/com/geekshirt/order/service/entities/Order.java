package com.geekshirt.order.service.entities;

import com.geekshirt.order.service.util.OrderPaymentStatus;
import com.geekshirt.order.service.util.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ORDERS")
public class Order extends CommonEntity{ // para implementar createdDate y lastUpdateDate

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ORDER_ID")
    private String orderId;
    @Column(name="STATUS")
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;
    @Column(name = "ACCOUNT_ID")
    private String accountId;
    @Column(name = "TOTAL_AMOUNT")
    private Double totalAmount;
    @Column(name = "TOTAL_TAX")
    private Double totalTax;
    @Column(name="TRANSACTION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;
    @Column(name = "TOTAL_AMOUNT_TAX")
    private Double totalAmountTax;
    @Column(name = "PAYMENT_STATUS")
    @Enumerated(value = EnumType.STRING)
    private OrderPaymentStatus paymentStatus;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderDetail> details;


}
