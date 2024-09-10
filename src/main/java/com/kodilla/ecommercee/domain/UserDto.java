package com.kodilla.ecommercee.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
public class UserDto {
    @Schema(description = "Unique identifier of the user", example = "1")
    private final Long userId;
    @Schema(description = "Username of the user", example = "TomCruise")
    private final String username;
    @Schema(description = "Name of the user", example = "Tom")
    private final String firstName;
    @Schema(description = "Surname of the user", example = "Cruise")
    private final String lastName;
    @Schema(description = "Email of the user", example = "tomcruise@gmail.com")
    private final String email;
    @Schema(description = "Password of the user", example = "Tom123")
    private final String password;
    @Schema(description = "Status of the user", example = "ACTIVE")
    private final UserStatus status;
    @Schema(description = "Temporary Key of the user", example = "1234")
    private final Integer temporaryKey;
    @Schema(description = "User creation date and time", example = "2024-09-01T18:15:30")
    private final LocalDate createDate;
}
