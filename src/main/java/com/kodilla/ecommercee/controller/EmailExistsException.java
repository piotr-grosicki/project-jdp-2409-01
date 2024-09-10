package com.kodilla.ecommercee.controller;

import lombok.Getter;

@Getter
public class EmailExistsException extends Exception {
    private String email;

    public EmailExistsException(String email) {
        super("Email " + email + " already exists.");
    }
}
