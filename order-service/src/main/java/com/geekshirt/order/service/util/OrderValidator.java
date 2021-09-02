package com.geekshirt.order.service.util;

import com.geekshirt.order.service.dto.OrderRequest;
import com.geekshirt.order.service.exceptions.IncorrectOrderRequestException;

public class OrderValidator {
    public static boolean validateOrder(OrderRequest orderRequest){
        if(orderRequest.getItems() == null || orderRequest.getItems().isEmpty()){
            throw new IncorrectOrderRequestException(ExceptionMessagesEnum.INCORRECT_REQUEST_EMPTY_ITEMS_ORDER.getValue());
        }
        return true;
    }
}
