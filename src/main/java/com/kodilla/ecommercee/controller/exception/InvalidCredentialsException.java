package com.kodilla.ecommercee.controller.exception;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException() {
        super("Invalid credentials: username or password.");
    }
}
