package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.UserStatus;
import jakarta.transaction.Transactional;
import jdk.jfr.Name;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DisplayName("Tests for CartRepository")
@SpringBootTest
@Transactional
public class CartRepositoryTests {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @DisplayName("Test case for saving cart")
    @Test
    void shouldSaveCart() {
        // Given
        User user = new User(null, "JaninaNowak", "Janina", "Nowak", "janina.nowak@mail.com", "another-strong-password", UserStatus.BLOCKED, 123456, LocalDate.of(2024, 2, 1), new ArrayList<>(), new ArrayList<>());

        User savedUser = userRepository.save(user);
        Long savedUserId = savedUser.getUserId();

        Cart cart = new Cart(
                2L,
                savedUser,
                new ArrayList<>(),
                LocalDateTime.of(2024,5,10, 15, 10, 0));

        Cart savedCart = cartRepository.save(cart);
        Long savedCartId = savedCart.getCartId();
        // When
        Optional<Cart> cartById = cartRepository.findById(savedCartId);
        // Then
        Assertions.assertTrue(cartById.isPresent());
        // CleanUp
        userRepository.deleteById(savedUserId);
        cartRepository.deleteById(savedCartId);
    }

    @DisplayName("Test case for deleting cart")
    @Test
    void shouldDeleteCart() {
        // Given
        User user = new User(null, "JaninaNowak", "Janina", "Nowak", "janina.nowak@mail.com", "another-strong-password", UserStatus.BLOCKED, 123456, LocalDate.of(2024, 2, 1), new ArrayList<>(), new ArrayList<>());

        User savedUser = userRepository.save(user);
        Long savedUserId = savedUser.getUserId();

        Cart cart = new Cart(
                null,
                savedUser,
                new ArrayList<>(),
                LocalDateTime.of(2024,5,10, 15, 10, 0));

        Cart savedCart = cartRepository.save(cart);
        Long savedCartId = savedCart.getCartId();
        // When
        cartRepository.deleteById(savedCartId);
        // Then
        Assertions.assertFalse(cartRepository.findById(savedCartId).isPresent());
        // CleanUp
        userRepository.deleteById(savedUserId);
    }

    @Name("Test for findById method")
    @Test
    public void shouldFindCartById() {
        // Given
        User user = new User(null, "JaninaNowak", "Janina", "Nowak", "janina.nowak@mail.com", "another-strong-password", UserStatus.BLOCKED, 123456, LocalDate.of(2024, 2, 1), new ArrayList<>(), new ArrayList<>());

        User savedUser = userRepository.save(user);
        Long savedUserId = savedUser.getUserId();

        Cart cart = new Cart(null,
                savedUser,
                new ArrayList<>(),
                LocalDateTime.of(2024,5,10, 15, 10, 0)
        );

        Cart savedCart = cartRepository.save(cart);
        Long savedCartId = savedCart.getCartId();
        // When
        Long result = cartRepository.findById(savedCartId).get().getCartId();
        // Then
        Assertions.assertEquals(savedCartId, result);
        // CleanUp
        cartRepository.deleteById(savedCartId);
    }

    @Name("Test for entity User related to Cart")
    @Test
    public void shouldNotDeleteUserAfterDeletingCart() {
        // Given
        User user = new User(null, "JaninaNowak", "Janina", "Nowak", "janina.nowak@mail.com", "another-strong-password", UserStatus.BLOCKED, 123456, LocalDate.of(2024, 2, 1), new ArrayList<>(), new ArrayList<>());

        User savedUser = userRepository.save(user);
        Long savedUserId = savedUser.getUserId();

        Cart cart = new Cart(null,
                savedUser,
                new ArrayList<>(),
                LocalDateTime.of(2024,5,10, 15, 10, 0)
        );

        Cart savedCart = cartRepository.save(cart);
        Long savedCartId = savedCart.getCartId();
        // When
        cartRepository.deleteById(savedCartId);
        // Then
        Assertions.assertTrue(userRepository.findById(savedUserId).isPresent());
        // CleanUp
        userRepository.deleteById(savedUserId);
    }

    @Name("Test for entity Product related to Cart")
    @Test
    public void shouldNotDeleteProductAfterDeletingCart() {
        // Given
        Product product = new Product(null, "Toyota", "Car", new BigDecimal(55000), 1, null, LocalDate.now());

        Product savedProduct = productRepository.save(product);
        Long savedProductId = savedProduct.getId();

        Cart cart = new Cart(null,
                null,
                new ArrayList<>(List.of(savedProduct)),
                LocalDateTime.of(2024,5,10, 15, 10, 0)
        );

        Cart savedCart = cartRepository.save(cart);
        Long savedCartId = savedCart.getCartId();
        // When
        cartRepository.deleteById(savedCartId);
        // Then
        Assertions.assertTrue(productRepository.findById(savedProductId).isPresent());
        // CleanUp
        productRepository.deleteById(savedProductId);
    }

    @Test
    @DisplayName("Test case for finding carts that contain a specific product")
    void shouldFindCartsContainingProduct() {
        // Given
        Product product1 = new Product(null, "Toyota1", "Car", new BigDecimal(55000), 1, null, LocalDate.now());
        Product savedProduct1 = productRepository.save(product1);

        User user = new User(null, "Bubus", "Bubuslaw", "Buslawska 1", "bubus@example.com", "bubus1", UserStatus.ACTIVE, 123, LocalDate.now(), null, null);
        User savedUser = userRepository.save(user);

        Cart cart1 = new Cart(null, user, new ArrayList<>(List.of(savedProduct1)), LocalDateTime.now());
        Cart cart2 = new Cart(null, user, new ArrayList<>(List.of(savedProduct1)), LocalDateTime.now());

        cartRepository.save(cart1);
        cartRepository.save(cart2);
        // When
        List<Cart> cartsContainingProduct1 = cartRepository.findByCartProductsContains(savedProduct1);
        // Then
        Assertions.assertEquals(2, cartsContainingProduct1.size());
        Assertions.assertTrue(cartsContainingProduct1.contains(cart1));
        Assertions.assertTrue(cartsContainingProduct1.contains(cart2));
    }
}
