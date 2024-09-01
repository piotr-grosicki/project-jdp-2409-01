package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.ProductDto;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/products")
public class ProductController {

    @GetMapping
    public List<ProductDto> getProducts() {
        return new ArrayList<>();
    }

    @GetMapping(value = "{productId}")
    public ProductDto getProduct(@PathVariable Long productId) {
        return new ProductDto(
                1L,
                "Bluetooth speaker",
                BigDecimal.valueOf(249),
                50,
                1,
                LocalDate.of(2024, 8, 31)
                );
    }

    @PostMapping
    public void createProduct(@RequestBody ProductDto productDto) {

    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        return new ProductDto(
                1L,
                "Bluetooth speaker plus",
                BigDecimal.valueOf(289),
                50,
                1,
                LocalDate.of(2024, 8,31)
        );
    }

    @DeleteMapping(value = "{productId}")
    public void deleteProduct(@PathVariable Long productId) {

    }

}
