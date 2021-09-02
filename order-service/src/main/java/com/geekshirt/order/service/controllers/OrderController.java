package com.geekshirt.order.service.controllers;

import com.geekshirt.order.service.dto.OrderRequest;
import com.geekshirt.order.service.dto.OrderResponse;
import com.geekshirt.order.service.entities.Order;
import com.geekshirt.order.service.exceptions.PaymentNotAcceptedException;
import com.geekshirt.order.service.services.OrderService;
import com.geekshirt.order.service.util.EntityDtoConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Api
@RestController
public class OrderController {

    Date date0 = new Date();

    @Autowired
    private OrderService orderService;

    @Autowired
    private EntityDtoConverter entityDtoConverter;


    @ApiOperation(value="This operation returns all existing orders", notes="Notas..")
    //el ResponseEntity agrega headers, si pusiera OrderResponse solo retornaria un body
    @GetMapping(value="order")
    public ResponseEntity<List<OrderResponse>> findAll(){
        List<Order> orders = orderService.findAllOrders();
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDto(orders), HttpStatus.OK);

    }

    @ApiOperation(value="This operation retrieves an order by Order Id")
    @GetMapping(value="order/{orderId}")
    public ResponseEntity<OrderResponse> findOrderById(@PathVariable String orderId){
        Order order = orderService.findOrderById(orderId);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDto(order), HttpStatus.OK);
    }

    @ApiOperation(value="This operation retrieves an order based on its DB Id")
    @GetMapping(value="order/generated/{orderId}")
    public ResponseEntity<OrderResponse> findByGeneratedId(@PathVariable long orderId){
        Order order = orderService.findById(orderId);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDto(order), HttpStatus.OK);
    }

    @ApiOperation(value="This operation creates a new order")
    @PostMapping(value="order/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest payload) throws PaymentNotAcceptedException {
        Order order = orderService.createOrder(payload);
        return new ResponseEntity<> (entityDtoConverter.convertEntityToDto(order), HttpStatus.CREATED);
    }

    @ApiOperation(value="This operation returns all orders of an account based on its id")
    @PostMapping(value="order/account/{accountId}")
    public ResponseEntity<List<OrderResponse>> findOrdersByAccountId(@PathVariable String accountId){
        List<Order> orders = orderService.findOrdersByAccountId(accountId);
        return new ResponseEntity<> (entityDtoConverter.convertEntityToDto(orders), HttpStatus.CREATED);
    }
}
