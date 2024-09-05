package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("v1/products")
@Tag(name = "Products", description = "Managing products")
public class ProductController {

    @Operation(
            description = "Get products list from database",
            summary = "Get products"
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok(
                List.of(new ProductDto(
                        2L,
                        "Socks",
                        "Big white socks",
                        BigDecimal.valueOf(2.99),
                        500,
                        18,
                        LocalDate.of(2024, 9, 6)
                ))
        );
    }

    @Operation(
            description = "Get product from database",
            summary = "Get product"
    )
    @GetMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(new ProductDto(
                1L,
                "Bluetooth speaker",
                "Great speaker",
                BigDecimal.valueOf(249),
                50,
                1,
                LocalDate.of(2024, 8, 31)
                )
        );
    }

    @Operation(
            description = "Create product in database",
            summary = "Create product"
    )
    @PostMapping(value = "/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            description = "Update product in database",
            summary = "Update product"
    )
    @PutMapping(value = "/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> updateProduct(@PathVariable Long productId, @RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(
            description = "Delete product from database",
            summary = "Delete product"
    )
    @DeleteMapping(value = "/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
