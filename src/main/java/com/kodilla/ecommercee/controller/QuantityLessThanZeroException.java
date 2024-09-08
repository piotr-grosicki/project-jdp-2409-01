package com.kodilla.ecommercee.controller;

public class QuantityLessThanZeroException extends Exception {
    public QuantityLessThanZeroException() {
        super("Quantity must be greater than zero.");
    }
}
