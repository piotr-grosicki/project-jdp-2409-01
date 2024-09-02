package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.OrderDto;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/orders")
public class OrderController {

    @GetMapping
    public List<OrderDto> getOrders() {
        // Zwraca pustą listę ze sztucznymi danymi
        return new ArrayList<>();
    }

    @GetMapping(value = "{orderId}")
    public OrderDto getOrder(@PathVariable Long orderId) {
        // Zwraca sztuczne dane dla zamówienia o danym ID
        return new OrderDto(
                1L,
                LocalDate.of(2024, 8, 31),
                BigDecimal.valueOf(499.99),
                "Delivered",
                1L
        );
    }

    @PostMapping
    public void createOrder(@RequestBody OrderDto orderDto) {
        // Endpoint do tworzenia nowego zamówienia, na razie pusty
    }

    @PutMapping
    public OrderDto updateOrder(@RequestBody OrderDto orderDto) {
        // Zwraca zaktualizowane dane zamówienia, na razie sztuczne
        return new OrderDto(
                1L,
                LocalDate.of(2024, 9, 1),
                BigDecimal.valueOf(599.99),
                "Processing",
                1L
        );
    }

    @DeleteMapping(value = "{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        // Endpoint do usuwania zamówienia, na razie pusty
    }
}
