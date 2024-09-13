package com.kodilla.ecommercee.controller.exception;

public class OrderNotFoundException extends Exception {
    private Long orderId;

    public OrderNotFoundException(Long orderId) {
        super(String.format("Order with ID %d not found.", orderId));
        this.orderId = orderId;
    }
}
