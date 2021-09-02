package com.geekshirt.order.service.handler;

import com.geekshirt.order.service.exceptions.AccountNotFoundException;
import com.geekshirt.order.service.exceptions.IncorrectOrderRequestException;
import com.geekshirt.order.service.exceptions.OrderServiceExceptionResponse;
import com.geekshirt.order.service.exceptions.PaymentNotAcceptedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class OrderServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> allExceptionHander(Exception exception, WebRequest webRequest){

        OrderServiceExceptionResponse response = new OrderServiceExceptionResponse(exception.getMessage(), webRequest.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());
        return new ResponseEntity<>(response, response.getStatus());
    }

    //este metodo atrapa todas las excepciones de tipo IncorrectOrderRequestException
    @ExceptionHandler(IncorrectOrderRequestException.class)
    public ResponseEntity<Object> incorrectRequestExceptionHander(Exception exception, WebRequest webRequest){

        OrderServiceExceptionResponse response = new OrderServiceExceptionResponse(exception.getMessage(), webRequest.getDescription(false), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Object> accountNotFoundExceptionHander(Exception exception, WebRequest webRequest){

        OrderServiceExceptionResponse response = new OrderServiceExceptionResponse(exception.getMessage(), webRequest.getDescription(false), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(PaymentNotAcceptedException.class)
    public ResponseEntity<Object> handlePaymentNotAcceptedResourceNotFound(PaymentNotAcceptedException exception, WebRequest request) {
        OrderServiceExceptionResponse response = new OrderServiceExceptionResponse(exception.getMessage(), request.getDescription(false), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<>(response, response.getStatus());
    }
}
