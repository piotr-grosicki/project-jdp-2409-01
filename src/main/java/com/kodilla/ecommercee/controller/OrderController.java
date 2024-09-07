package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.OrderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/orders")
@Tag(name = "Orders", description = "Managing orders")
public class OrderController {

    @Operation(
            description = "Retrieve all orders from the system",
            summary = "Get all orders"
    )
    @GetMapping
    public List<OrderDto> getOrders() {
        // Zwraca pustą listę ze sztucznymi danymi
        return new ArrayList<>();
    }

    // Added constructor in OrderDto to make possible OrderController work appropriate //
    @Operation(
            description = "Retrieve a specific order by its ID",
            summary = "Get order by ID"
    )

    @GetMapping("/{orderId}")
    public OrderDto getOrder(@PathVariable Long orderId) {
        // Zwraca sztuczne dane dla zamówienia o danym ID
        return new OrderDto(
                orderId,
                1L, // userId
                1L, // cartId
                "Delivered", // status
                LocalDateTime.of(2024, 8, 31, 18, 15, 30), // creationDate
                BigDecimal.valueOf(499.99) // totalAmount
        );
    }

    @Operation(
            description = "Create a new order in the system",
            summary = "Create order"
    )
    @PostMapping
    public void createOrder(@RequestBody OrderDto orderDto) {
        // Endpoint do tworzenia nowego zamówienia, na razie pusty
    }

    @Operation(
            description = "Update an existing order",
            summary = "Update order"
    )
    @PutMapping("/{orderId}")
    public OrderDto updateOrder(@PathVariable Long orderId, @RequestBody OrderDto orderDto) {
        return new OrderDto(
                orderId,
                orderDto.getUserId(),
                orderDto.getCartId(),
                orderDto.getStatus(),
                orderDto.getCreationDate(),
                orderDto.getTotalAmount()
        );
    }

    @Operation(
            description = "Delete a specific order by its ID",
            summary = "Delete order"
    )
    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        // Endpoint do usuwania zamówienia, na razie pusty
    }
}
