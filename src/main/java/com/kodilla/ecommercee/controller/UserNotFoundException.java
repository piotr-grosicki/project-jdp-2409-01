package com.kodilla.ecommercee.controller;

import lombok.Getter;

@Getter
public class UserNotFoundException extends Exception {
    private Long userId;

    public UserNotFoundException(Long userId) {
        super("User with ID " + userId + " does not exist.");
        this.userId = userId;
    }
}
