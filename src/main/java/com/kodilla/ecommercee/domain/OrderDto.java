package com.kodilla.ecommercee.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class OrderDto {
    @Schema(description = "Unique identifier of the order", example = "1")
    private Long id;
    @Schema(description = "User identifier", example = "1")
    private Long userId;
    @Schema(description = "Cart identifier", example = "1")
    private Long cartId;
    @Schema(description = "Status of the order", example = "NEW")
    private String status;
    @Schema(description = "Order creation date and time", example = "2024-09-01T18:15:30")
    private LocalDateTime creationDate;
    @Schema(description = "Total amount to be paid for the order", example = "150.00")
    private BigDecimal totalAmount;
}
