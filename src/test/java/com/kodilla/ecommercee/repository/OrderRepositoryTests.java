package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@DisplayName("Tests for OrderRepository")
@SpringBootTest
public class OrderRepositoryTests {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;

    @DisplayName("Test case for save method")
    @Test
    void shouldSaveOrder() {
        // Given
        User user = new User(null, "Username", "FirstName", "LastName", "Email", "Password", UserStatus.ACTIVE, LocalDate.of(2024, 9, 13));
        User savedUser = userRepository.save(user);
        Long userId = savedUser.getUserId();

        Cart cart = new Cart(null, savedUser, new ArrayList<>(), LocalDateTime.now());
        Cart savedCart = cartRepository.save(cart);
        Long cartId = savedCart.getCartId();

        Order order = new Order(null,null, new BigDecimal("100.00"), LocalDateTime.now(), OrderStatus.CREATED,savedCart);
        Order savedOrder = orderRepository.save(order);
        Long savedOrderId = savedOrder.getOrderId();
        // When
        Optional<Order> retrievedOrder = orderRepository.findById(savedOrderId);
        // Then
        Assertions.assertTrue(retrievedOrder.isPresent());
        Assertions.assertEquals(new BigDecimal("100.00"), retrievedOrder.get().getTotal());
        Assertions.assertEquals(OrderStatus.CREATED, retrievedOrder.get().getStatus());
        // CleanUp
        orderRepository.deleteById(savedOrderId);
        cartRepository.deleteById(cartId);
        userRepository.deleteById(userId);
    }

    @DisplayName("Test case for delete method")
    @Test
    void shouldDeleteOrder() {
        // Given
        User user = new User(null, "Username", "FirstName", "LastName", "Email", "Password", UserStatus.ACTIVE, LocalDate.of(2024, 9, 13));
        User savedUser = userRepository.save(user);
        Long userId = savedUser.getUserId();

        Cart cart = new Cart(null, savedUser, new ArrayList<>(), LocalDateTime.now());
        Cart savedCart = cartRepository.save(cart);
        Long cartId = savedCart.getCartId();

        Order order = new Order(null, null, new BigDecimal("100.00"), LocalDateTime.now(), OrderStatus.CREATED, savedCart);
        Order savedOrder = orderRepository.save(order);
        Long orderId = savedOrder.getOrderId();
        // When
        orderRepository.deleteById(orderId);
        // Then
        Optional<Order> retrievedOrder = orderRepository.findById(orderId);
        Assertions.assertFalse(retrievedOrder.isPresent());
        // CleanUp
        cartRepository.deleteById(cartId);
        userRepository.deleteById(userId);
    }

    @DisplayName("Test case for delete an order: should not delete user after deleting orders of this user")
    @Test
    void shouldNotDeleteUserAfterDeletingOrder() {
        // Given
        User user = new User(null, "Username", "FirstName", "LastName", "Email", "Password", UserStatus.ACTIVE, LocalDate.of(2024, 9, 13));
        User savedUser = userRepository.save(user);
        Long userId = savedUser.getUserId();

        Cart cart = new Cart(null, savedUser, new ArrayList<>(), LocalDateTime.now());
        Cart savedCart = cartRepository.save(cart);
        Long cartId = savedCart.getCartId();

        Order order = new Order(null, savedUser, new BigDecimal("100.00"), LocalDateTime.now(), OrderStatus.CREATED, savedCart);
        Order savedOrder = orderRepository.save(order);
        Long orderId = savedOrder.getOrderId();
        // When
        orderRepository.deleteById(orderId);
        // Then
        Optional<User> retrievedUser = userRepository.findById(userId);
        Assertions.assertTrue(retrievedUser.isPresent());
        // CleanUp
        cartRepository.deleteById(cartId);
        userRepository.deleteById(userId);
    }

    @DisplayName("Test case for delete an order: should not delete cart after deleting the order related to this cart")
    @Test
    void shouldNotDeleteCartAfterDeletingOrder() {
        // Given
        User user = new User(null, "Username", "FirstName", "LastName", "Email", "Password", UserStatus.ACTIVE, LocalDate.of(2024, 9, 13));
        User savedUser = userRepository.save(user);
        Long userId = savedUser.getUserId();

        Cart cart = new Cart(null, savedUser, new ArrayList<>(), LocalDateTime.now());
        Cart savedCart = cartRepository.save(cart);
        Long cartId = savedCart.getCartId();

        Order order = new Order(null, savedUser, new BigDecimal("100.00"), LocalDateTime.now(), OrderStatus.CREATED, savedCart);
        Order savedOrder = orderRepository.save(order);
        Long orderId = savedOrder.getOrderId();
        // When
        orderRepository.deleteById(orderId);
        // Then
        Optional<Cart> retrievedCart = cartRepository.findById(cartId);
        Assertions.assertTrue(retrievedCart.isPresent());
        // CleanUp
        cartRepository.deleteById(cartId);
        userRepository.deleteById(userId);
    }
}
