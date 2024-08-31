package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.ProductDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductController {

    public List<ProductDto> getProducts() {
        return new ArrayList<>();
    }

    public ProductDto getProduct(Long productId) {
        return new ProductDto(
                1L,
                "Bluetooth speaker",
                BigDecimal.valueOf(249),
                50,
                1,
                LocalDate.of(2024, 8, 31)
                );
    }

    public void createProduct(ProductDto productDto) {

    }

    public ProductDto updateProduct(ProductDto productDto) {
        return new ProductDto(
                1L,
                "Bluetooth speaker plus",
                BigDecimal.valueOf(289),
                50,
                1,
                LocalDate.of(2024, 8,31)
        );
    }

    public void deleteProduct(Long productId) {

    }

}
