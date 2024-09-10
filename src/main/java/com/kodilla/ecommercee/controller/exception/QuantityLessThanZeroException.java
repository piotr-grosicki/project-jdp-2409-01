package com.kodilla.ecommercee.controller.exception;

public class QuantityLessThanZeroException extends Exception {
    public QuantityLessThanZeroException() {
        super("Quantity must be greater than zero.");
    }
}
