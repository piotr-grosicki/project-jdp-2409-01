package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.User;
import jdk.jfr.Name;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@DisplayName("Tests for CartRepository")
@SpringBootTest
public class CartRepositoryTests {

    @Autowired
    private CartRepository cartRepository;

    @DisplayName("Test case for saving cart")
    @Test
    void shouldSaveCart() {
        // Given
        Cart cart = new Cart(
                2L,
                new User(
                        11002L,
                        "JanNowak",
                        "Jan",
                        "Nowak",
                        "jan.nowak@mail.com",
                        "very-strong-password",
                        "Active",
                        123456,
                        LocalDate.of(2024, 2, 1),
                        new ArrayList<>(),
                        new ArrayList<>()
                ),
                new ArrayList<>(),
                LocalDateTime.of(2024,5,10, 15, 10, 0));
        Cart savedCart = cartRepository.save(cart);
        Long savedCartId = savedCart.getCartId();

        // When
        Optional<Cart> cartById = cartRepository.findById(savedCartId);

        // Then
        Assertions.assertTrue(cartById.isPresent());

        // CleanUp
        cartRepository.deleteById(savedCartId);

    }

    @DisplayName("Test case for deleting cart")
    @Test
    void shouldDeleteCart() {
        // Given
        Cart cart = new Cart(
                101L,
                new User(
                        11001L,
                        "JaninaNowak",
                        "Janina",
                        "Nowak",
                        "janina.nowak@mail.com",
                        "another-strong-password",
                        "Blocked",
                        123456,
                        LocalDate.of(2024, 2, 1),
                        new ArrayList<>(),
                        new ArrayList<>()
                ),
                new ArrayList<>(),
                LocalDateTime.of(2024,5,10, 15, 10, 0));
        Cart savedCart = cartRepository.save(cart);
        Long savedCartId = savedCart.getCartId();

        // When
        cartRepository.deleteById(savedCartId);
        Optional<Cart> cartById = cartRepository.findById(savedCartId);

        // Then
        Assertions.assertFalse(cartById.isPresent());

        // CleanUp
        // done earlier

    }

    @Name("Test for findById method")
    @Test
    public void shouldFindCartById() {
        // Given
        Cart cart = new Cart(
                101L,
                new User(
                        11001L,
                        "JaninaNowak",
                        "Janina",
                        "Nowak",
                        "janina.nowak@mail.com",
                        "another-strong-password",
                        "Blocked",
                        123456,
                        LocalDate.of(2024, 2, 1),
                        new ArrayList<>(),
                        new ArrayList<>()
                ),
                new ArrayList<>(),
                LocalDateTime.of(2024,5,10, 15, 10, 0)
        );
        Cart savedCart = cartRepository.save(cart);
        // When
        Long savedCartId = savedCart.getCartId();
        // Then
        Assertions.assertEquals(101L, savedCartId);
        // CleanUp
        cartRepository.deleteById(savedCartId);
    }
}
