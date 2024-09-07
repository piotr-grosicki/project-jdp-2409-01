package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.ProductDto;
import com.kodilla.ecommercee.mapper.ProductMapper;
import com.kodilla.ecommercee.service.ProductDbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/products")
@Tag(name = "Products", description = "Managing products")
public class ProductController {

    private final ProductMapper productMapper;
    private final ProductDbService productDbService;

    @Operation(
            description = "Get products list from database",
            summary = "Get products"
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<Product> products = productDbService.getAllProducts();
        return ResponseEntity.ok(productMapper.mapToProductDtoList(products));
    }

    @Operation(
            description = "Get product from database",
            summary = "Get product"
    )
    @GetMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long productId) throws ProductNotFoundException {
        return ResponseEntity.ok(productMapper.productToProductDto(productDbService.getProduct(productId)));
    }

    @Operation(
            description = "Create product in database",
            summary = "Create product"
    )
    @PostMapping(value = "/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createProduct(@RequestBody ProductDto productDto) {
        Product product = productMapper.productDtoToProduct(productDto);
        productDbService.saveProduct(product);
        return ResponseEntity.ok().build();
    }

    @Operation(
            description = "Update product in database",
            summary = "Update product"
    )
    @PutMapping(value = "/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId, @RequestBody ProductDto productDto) {
        Product product = productMapper.productDtoToProduct(productDto);
        Product savedProduct = productDbService.saveProduct(product);
        return ResponseEntity.ok(productMapper.productToProductDto(savedProduct));
    }

    @Operation(
            description = "Delete product from database",
            summary = "Delete product"
    )
    @DeleteMapping(value = "/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) throws ProductNotFoundException {
        boolean isDeleted = productDbService.deleteProduct(productId);
        if(isDeleted){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
