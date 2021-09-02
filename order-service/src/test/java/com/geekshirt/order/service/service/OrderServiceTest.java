package com.geekshirt.order.service.service;

import com.geekshirt.order.service.services.OrderService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;

}
