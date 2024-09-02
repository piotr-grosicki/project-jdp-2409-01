package com.kodilla.ecommercee.domain;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class UserDto {

    private final Long userId;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final String status;
    private final int temporaryKey;
    private final LocalDate createDate;
}
