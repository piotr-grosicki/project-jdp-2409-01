package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/products")
@Tag(name = "Products", description = "Managing products")
public class ProductController {

    @Operation(
            description = "Get products list from database",
            summary = "Get products"
    )
    @GetMapping
    public List<ProductDto> getProducts() {
        return new ArrayList<>();
    }

    @Operation(
            description = "Get product from database",
            summary = "Get product"
    )
    @GetMapping(value = "{productId}")
    public ProductDto getProduct(@PathVariable Long productId) {
        return new ProductDto(
                1L,
                "Bluetooth speaker",
                "Some nice speaker",
                BigDecimal.valueOf(249),
                50,
                1,
                LocalDate.of(2024, 8, 31)
                );
    }

    @Operation(
            description = "Create product in database",
            summary = "Create product"
    )
    @PostMapping
    public void createProduct(@RequestBody ProductDto productDto) {

    }

    @Operation(
            description = "Update product in database",
            summary = "Update product"
    )
    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        return new ProductDto(
                1L,
                "Bluetooth speaker plus",
                "Speaker with extra function",
                BigDecimal.valueOf(289),
                50,
                1,
                LocalDate.of(2024, 8,31)
        );
    }

    @Operation(
            description = "Delete product from database",
            summary = "Delete product"
    )
    @DeleteMapping(value = "{productId}")
    public void deleteProduct(@PathVariable Long productId) {

    }

}
