package com.kodilla.ecommercee.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserStatusDto {
    @Schema(description = "Status of the user to be set", example = "BLOCKED")
    private UserStatus status;
}
