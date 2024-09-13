package com.kodilla.ecommercee.controller.exception;

import com.kodilla.ecommercee.domain.OrderStatus;
import lombok.Getter;

@Getter
public class InvalidOrderStatusException extends Exception {
    private OrderStatus orderStatus;

    public InvalidOrderStatusException(OrderStatus orderStatus) {
        super("Status " + orderStatus + " for order cannot be changed.");
        this.orderStatus = orderStatus;
    }
}
