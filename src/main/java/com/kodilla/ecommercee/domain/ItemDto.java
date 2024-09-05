package com.kodilla.ecommercee.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ItemDto {
    @Schema(description = "Unique product identifier", example = "1")
    private Long productId;
    @Schema(description = "Product quantity", example = "1")
    private Long productQuantity;
}
