package com.kodilla.ecommercee.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CartDto {
    @Schema(description = "Unique identifier of the cart", example = "1")
    private Long id;
    @Schema(description = "User identifier", example = "1")
    private Long userId;
}
