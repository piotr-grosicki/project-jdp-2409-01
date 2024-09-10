package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.OrderDto;
import com.kodilla.ecommercee.controller.exception.UserNotFoundException;
import com.kodilla.ecommercee.controller.exception.CartNotFoundException;
import com.kodilla.ecommercee.controller.exception.OrderNotFoundException;
import com.kodilla.ecommercee.service.OrderDbService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderDbService orderDbService;

    public OrderController(OrderDbService orderDbService) {
        this.orderDbService = orderDbService;
    }

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
}
