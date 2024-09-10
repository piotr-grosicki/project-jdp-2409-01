package com.kodilla.ecommercee.controller;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long orderId) {
        super(String.format("Order with ID %d not found", orderId));
    }
}
