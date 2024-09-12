package com.kodilla.ecommercee.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
public class UserDto {
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
}
