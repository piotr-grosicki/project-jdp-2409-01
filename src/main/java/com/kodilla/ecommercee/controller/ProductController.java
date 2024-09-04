package com.kodilla.ecommercee.controller;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.kodilla.ecommercee.domain.ProductDto;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/products")
public class ProductController {

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

    @PostMapping(value = "/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> updateProduct(@PathVariable Long productId, @RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(value = "/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
