package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.controller.exception.InvalidOrderStatusException;
import com.kodilla.ecommercee.domain.OrderDto;
import com.kodilla.ecommercee.controller.exception.OrderNotFoundException;
import com.kodilla.ecommercee.domain.UpdateOrderDto;
import com.kodilla.ecommercee.service.OrderDbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/orders")
@Tag(name = "Orders", description = "Managing orders")
public class OrderController {
    private final OrderDbService orderDbService;

    @Operation(
            description = "Fetches a list of all existing orders",
            summary = "Retrieve all orders"
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OrderDto>> getOrders() {
        List<OrderDto> orders = orderDbService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @Operation(
            description = "Fetches a single order based on its unique ID",
            summary = "Retrieve an order"
    )
    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId) throws OrderNotFoundException {
            OrderDto orderDto = orderDbService.getOrderById(orderId);
            return ResponseEntity.ok(orderDto);
    }

    @Operation(
            description = "Adds a new order that was previously created based on the cart",
            summary = "Add a new order"
    )
    @PostMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrderDto> addOrder(@PathVariable Long orderId) throws InvalidOrderStatusException, OrderNotFoundException {
        OrderDto addedOrder = orderDbService.addOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(addedOrder);
    }

    @Operation(
            description = "Updates an existing order identified by its ID",
            summary = "Update an existing order"
    )
    @PutMapping(value = "/modify/{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long orderId, @RequestBody UpdateOrderDto updateOrderDto) throws OrderNotFoundException {
        OrderDto updatedOrder = orderDbService.updateOrder(orderId, updateOrderDto);
        return ResponseEntity.ok(updatedOrder);
    }

    @Operation(
            description = "Deletes an existing order identified by its ID",
            summary = "Delete an existing order"
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
