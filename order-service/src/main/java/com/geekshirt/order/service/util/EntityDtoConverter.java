package com.geekshirt.order.service.util;

import com.geekshirt.order.service.dto.OrderResponse;
import com.geekshirt.order.service.entities.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityDtoConverter {

    @Autowired
    private ModelMapper modelMapper;

    public OrderResponse convertEntityToDto(Order order){
        return modelMapper.map(order, OrderResponse.class);
    }

    public List<OrderResponse> convertEntityToDto(List<Order> orders){
        return orders.stream()
                .map(order -> convertEntityToDto(order))
                .collect(Collectors.toList());
    }
}
