package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.OrderStatus;
import com.kodilla.ecommercee.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@DisplayName("Tests for OrderRepository")
@SpringBootTest
public class OrderRepositoryTests {

    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("Test case for save method")
    @Test
    void shouldSaveOrder() {
        // Given
        Order order = new Order(null,null, new BigDecimal("100.00"), LocalDateTime.now(), OrderStatus.CREATED,null); // Temporarily set orderId to 0 or a placeholder value
        Order savedOrder = orderRepository.save(order);
        Long savedOrderId = savedOrder.getOrderId(); // Get the actual ID assigned by the database
        // When
        Optional<Order> retrievedOrder = orderRepository.findById(savedOrderId);
        // Then
        Assertions.assertTrue(retrievedOrder.isPresent());
        Assertions.assertEquals(new BigDecimal("100.00"), retrievedOrder.get().getTotal());
        Assertions.assertEquals(OrderStatus.CREATED, retrievedOrder.get().getStatus());
        // CleanUp
        orderRepository.deleteById(savedOrderId);
    }

    @DisplayName("Test case for delete method")
    @Test
    void shouldDeleteOrder() {
        // Given
        Order order = new Order(1L,null, new BigDecimal("100.00"), LocalDateTime.now(), OrderStatus.CREATED,null); // Temporarily set orderId to 0 or a placeholder value
        Order savedOrder = orderRepository.save(order);
        Long savedOrderId = savedOrder.getOrderId(); // Get the actual ID assigned by the database
        // When
        orderRepository.deleteById(savedOrderId);
        // Then
        Optional<Order> retrievedOrder = orderRepository.findById(savedOrderId);
        Assertions.assertFalse(retrievedOrder.isPresent());
        // CleanUp
        orderRepository.deleteById(savedOrderId);
    }
}
