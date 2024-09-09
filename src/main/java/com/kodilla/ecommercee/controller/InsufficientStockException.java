package com.kodilla.ecommercee.controller;

import lombok.Getter;

@Getter
public class InsufficientStockException extends Exception {
    private Long productId;

    public InsufficientStockException(Long productId) {
        super("Not enough stock for product with ID " + productId);
    }
}
