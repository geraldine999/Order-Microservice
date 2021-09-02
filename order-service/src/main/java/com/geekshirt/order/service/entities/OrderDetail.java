package com.geekshirt.order.service.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name= "ORDER_DETAILS")
public class OrderDetail extends CommonEntity{ // para implementar createdDate y lastUpdateDate

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "QUANTITY")
    private Integer quantity;

    @Column(name="PRICE")
    private Double price;

    @Column(name= "TAX")
    private Double tax;

    @Column(name= "UPC")
    private String upc;

   @ManyToOne(cascade = CascadeType.ALL)
    private Order order;
}
