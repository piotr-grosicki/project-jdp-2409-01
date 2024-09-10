package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.OrderDto;
import com.kodilla.ecommercee.service.OrderDbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/orders")
@Tag(name = "Orders", description = "Managing orders")
public class OrderController {

    private final OrderDbService orderDbService;

    public OrderController(OrderDbService orderDbService) {
        this.orderDbService = orderDbService;
    }

    @Operation(
            description = "Retrieve all orders from the system",
            summary = "Get all orders"
    )
    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders() {
        List<OrderDto> orders = orderDbService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @Operation(
            description = "Retrieve a specific order by its ID",
            summary = "Get order by ID"
    )
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId) {
        try {
            OrderDto orderDto = orderDbService.getOrderById(orderId);
            return ResponseEntity.ok(orderDto);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(
            description = "Create a new order in the system",
            summary = "Create order"
    )
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        try {
            OrderDto createdOrder = orderDbService.createOrder(orderDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (UserNotFoundException | CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Operation(
            description = "Update an existing order",
            summary = "Update order"
    )
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long orderId, @RequestBody OrderDto orderDto) {
        try {
            OrderDto updatedOrder = orderDbService.updateOrder(orderId, orderDto);
            return ResponseEntity.ok(updatedOrder);
        } catch (OrderNotFoundException | UserNotFoundException | CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(
            description = "Delete a specific order by its ID",
            summary = "Delete order"
    )
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        boolean isDeleted = orderDbService.deleteOrder(orderId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
