package com.kodilla.ecommercee.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EventDetailDto {
    @Schema(description = "The specific key representing the type of detail for the event", example = "USER_ID")
    private String key;
    @Schema(description = "The value corresponding to the key", example = "1")
    private String value;
}

