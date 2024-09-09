package com.kodilla.ecommercee.controller;

import lombok.Getter;

@Getter
public class ProductNotFoundException extends Exception {
    private Long productId;

    public ProductNotFoundException(Long productId) {
        super("Product with ID " + productId + " does not exist.");
        this.productId = productId;
    }
}
