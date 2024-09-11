package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.OrderDto;
import com.kodilla.ecommercee.controller.exception.UserNotFoundException;
import com.kodilla.ecommercee.controller.exception.CartNotFoundException;
import com.kodilla.ecommercee.controller.exception.OrderNotFoundException;
import com.kodilla.ecommercee.service.OrderDbService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderDbService orderDbService;

    @Operation(
            description = "Retrieve all orders",
            summary = "Get all orders"
    )
    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders() {
        List<OrderDto> orders = orderDbService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @Operation(
            description = "Retrieve a specific order",
            summary = "Get order by id"
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
            description = "Create a new order",
            summary = "Create order"
    )
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) throws UserNotFoundException, CartNotFoundException {
        OrderDto createdOrder = orderDbService.createOrder(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @Operation(
            description = "Update an existing order",
            summary = "Update order"
    )
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long orderId, @RequestBody OrderDto orderDto) throws OrderNotFoundException, UserNotFoundException, CartNotFoundException {
        OrderDto updatedOrder = orderDbService.updateOrder(orderId, orderDto);
        return ResponseEntity.ok(updatedOrder);
    }

    @Operation(
            description = "Delete order by id",
            summary = "Delete order"
    )
    @DeleteMapping("/{orderdId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderdId) {
        boolean isDeleted = orderDbService.deleteOrder(orderdId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
