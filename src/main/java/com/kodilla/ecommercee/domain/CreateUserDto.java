package com.kodilla.ecommercee.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateUserDto {
    @Schema(description = "Username of the user", example = "TomCruise", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    @Schema(description = "Name of the user", example = "Tom", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String firstName;
    @Schema(description = "Surname of the user", example = "Cruise", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String lastName;
    @Schema(description = "Email of the user", example = "tomcruise@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    @Schema(description = "Password of the user", example = "Tom123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
