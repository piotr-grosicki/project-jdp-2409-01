package com.kodilla.ecommercee.controller;

import lombok.Getter;

@Getter
public class CartNotFoundException extends Exception {
    private Long cartId;

    public CartNotFoundException(Long cartId) {
        super("Cart with ID " + cartId + " does not exist.");
        this.cartId = cartId;
    }
}
