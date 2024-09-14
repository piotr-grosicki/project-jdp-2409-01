package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.*;
import jdk.jfr.Name;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Name("Tests for UserRepository")
@SpringBootTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Name("Test for save method")
    @Test
    public void shouldSaveUser() {
        //Given
        User user = new User(1L,"CarolD","Carol","Denver", "carold@gmail.com", "Carol123", UserStatus.ACTIVE, LocalDate.of(2024,9,6));
        User savedUser = userRepository.save(user);
        Long userId = savedUser.getUserId();
        //When
        Optional<User> userOptional = userRepository.findById(userId);
        //Then
        Assertions.assertTrue(userOptional.isPresent());
        Assertions.assertEquals("CarolD", userOptional.get().getUsername());
        //CleanUp
        userRepository.deleteById(userId);
    }

    @Name("Test for changing status")
    @Test
    public void shouldChangeUserStatus() {
        //Given
        User user = new User(1L,"CarolD","Carol","Denver", "carold@gmail.com", "Carol123", UserStatus.ACTIVE,  LocalDate.of(2024,9,6));
        User savedUser = userRepository.save(user);
        Long userId = savedUser.getUserId();
        //When
        //changin status
        UserStatus beforeChange = savedUser.getStatus();
        savedUser.setStatus(UserStatus.BLOCKED);
        userRepository.save(savedUser);
        UserStatus afterChange = savedUser.getStatus();
        //Then
        Assertions.assertEquals("ACTIVE", beforeChange.toString());
        Assertions.assertEquals("BLOCKED", afterChange.toString());
        //CleanUp
        userRepository.deleteById(userId);
    }

    @Name("Test for findById method")
    @Test
    public void shouldFindUserById() {
        // Given
        User user = new User(null,"CarolD","Carol","Denver", "carold@gmail.com", "Carol123", UserStatus.ACTIVE,  LocalDate.of(2024,9,6));
        User savedUser = userRepository.save(user);
        Long userId = savedUser.getUserId();
        // When
        Optional<User> result = userRepository.findById(userId);
        // Then
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(userId, result.get().getUserId());
        // CleanUp
        userRepository.deleteById(userId);
    }

    @Name("Test for findByUsername method")
    @Test
    public void shouldFindUserByUsername() {
        // Given
        User user = new User(null,"CarolD","Carol","Denver", "carold@gmail.com", "Carol123", UserStatus.ACTIVE, LocalDate.of(2024,9,6));
        User savedUser = userRepository.save(user);
        Long userId = savedUser.getUserId();
        // When
        Optional<User> result = userRepository.findByUsername("CarolD");
        // Then
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("CarolD", result.get().getUsername());
        // CleanUp
        userRepository.deleteById(userId);
    }

    @Name("Test for findByEmail method")
    @Test
    public void shouldFindUserByEmail() {
        // Given
        User user = new User(null,"CarolD","Carol","Denver", "carold@gmail.com", "Carol123", UserStatus.ACTIVE, LocalDate.of(2024,9,6));
        User savedUser = userRepository.save(user);
        Long userId = savedUser.getUserId();
        // When
        Optional<User> result = userRepository.findByEmail("carold@gmail.com");
        // Then
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("carold@gmail.com", result.get().getEmail());
        // CleanUp
        userRepository.deleteById(userId);
    }

    @Name("Test for deleting user having an existing cart: removing user should return integrity constraint violation")
    @Test
    public void shouldReturnIntegrityConstraintViolationAfterDeletingUser() {
        // Given
        User user = new User(null,"CarolD","Carol","Denver", "carold@gmail.com", "Carol123", UserStatus.ACTIVE, LocalDate.of(2024,9,13));
        User savedUser = userRepository.save(user);
        Long userId = savedUser.getUserId();

        Cart cart = new Cart(null, user, new ArrayList<>(), LocalDateTime.of(2024,9,13, 12, 0, 0));
        Cart savedCart = cartRepository.save(cart);
        Long cartId = savedCart.getCartId();

        // When & Then
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {userRepository.deleteById(userId);});

        Optional<User> deletedUser = userRepository.findById(userId);
        Assertions.assertTrue(deletedUser.isPresent());
        // CleanUp
        cartRepository.deleteById(cartId);
        userRepository.deleteById(userId);
    }

    @Test
    @Name("Test for deleting user having an existing order: removing user should return integrity constraint violation")
    public void shouldReturnIntegrityConstraintViolationAfterDeletingUserWithOrder() {
        // Given
        User user = new User(null,"CarolD","Carol","Denver", "carold@gmail.com", "Carol123", UserStatus.ACTIVE, LocalDate.of(2024,9,13));
        User savedUser = userRepository.save(user);
        Long userId = savedUser.getUserId();

        Cart cart = new Cart(2L, savedUser, new ArrayList<>(), LocalDateTime.of(2024,5,10, 15, 10, 0));
        Cart savedCart = cartRepository.save(cart);
        Long cartId = savedCart.getCartId();

        Order order = new Order(null, savedUser, new BigDecimal("100.00"), LocalDateTime.of(2024, 9, 13, 12, 0, 0), OrderStatus.NEW, savedCart);
        Order savedOrder = orderRepository.save(order);
        Long orderId = savedOrder.getOrderId();

        // When & Then
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {userRepository.deleteById(userId);});

        Optional<User> deletedUser = userRepository.findById(userId);
        Assertions.assertTrue(deletedUser.isPresent());
        // CleanUp
        orderRepository.deleteById(orderId);
        cartRepository.deleteById(cartId);
        userRepository.deleteById(userId);
    }
}
