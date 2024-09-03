package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.CartDto;
import com.kodilla.ecommercee.domain.ItemDto;
import com.kodilla.ecommercee.domain.OrderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/carts")
@Tag(name = "Carts", description = "Managing carts")
public class CartController {
    @Operation(
            description = "Creating an empty cart for user",
            summary = "Create a cart"
    )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CartDto> createCart(@RequestHeader("userId") Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CartDto(1L, userId)
        );
    }

    @Operation(
            description = "Receiving items from a cart by its cart ID",
            summary = "Get items"
    )
    @GetMapping(value = "/{cartId}/items", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ItemDto>> getItems(@PathVariable Long cartId) {
        return ResponseEntity.ok(new ArrayList<>(
                List.of(
                        new ItemDto(1L, 10L)
                )
        ));
    }

    @Operation(
            description = "Creating an item in cart",
            summary = "Create an item"
    )
    @PostMapping(value = "/{cartId}/items", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createItem(@RequestBody ItemDto itemDto) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            description = "Deleting an item from cart",
            summary = "Delete an item")
    @DeleteMapping(value = "/{cartId}/items/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long cartId,
            @PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            description = "Creating an order for cart",
            summary = "Create an order"
    )
    @PostMapping(value = "/{cartId}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderDto> createOrder(@PathVariable Long cartId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new OrderDto(
                        1L,
                        1L,
                        cartId,
                        "NEW",
                        LocalDateTime.now(),
                        new BigDecimal("111.11"))
        );
    }
}
