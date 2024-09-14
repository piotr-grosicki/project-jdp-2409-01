package com.kodilla.ecommercee.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class ProductDto {

    @Schema(description = "Unique identifier of the product", example = "1")
    private final Long productId;
    @Schema(description = "Product name of the product", example = "Smartphone")
    private final String productName;
    @Schema(description = "Description of the product", example = "6-inch")
    private final String description;
    @Schema(description = "Product price", example = "299.99")
    private final BigDecimal price;
    @Schema(description = "Quantity of the product", example = "100")
    private final int quantity;
    @Schema(description = "Id group for product", example = "1")
    private final Long productGroupId;
    @Schema(description = "Product creation date", example = "2024-09-03T20:30:45")
    private final LocalDate creationDate;
}
