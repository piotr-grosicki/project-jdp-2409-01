package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
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
        Order order = new Order(1,null,100.0, LocalDate.now(), "NEW",null); // Temporarily set orderId to 0 or a placeholder value
        Order savedOrder = orderRepository.save(order);
        Integer savedOrderId = savedOrder.getOrderId(); // Get the actual ID assigned by the database
        // When
        Optional<Order> retrievedOrder = orderRepository.findById(savedOrderId);
        // Then
        Assertions.assertTrue(retrievedOrder.isPresent());
        Assertions.assertEquals(100.0, retrievedOrder.get().getTotal());
        Assertions.assertEquals("NEW", retrievedOrder.get().getStatus());
    }

    @DisplayName("Test case for delete method")
    @Test
    void shouldDeleteOrder() {
        // Given
        Order order = new Order(1,null, 200.0, LocalDate.now(), "PENDING",null); // Temporarily set orderId to 0 or a placeholder value
        Order savedOrder = orderRepository.save(order);
        Integer savedOrderId = savedOrder.getOrderId(); // Get the actual ID assigned by the database
        // When
        orderRepository.deleteById(savedOrderId);
        // Then
        Optional<Order> retrievedOrder = orderRepository.findById(savedOrderId);
        Assertions.assertFalse(retrievedOrder.isPresent());
    }
}
