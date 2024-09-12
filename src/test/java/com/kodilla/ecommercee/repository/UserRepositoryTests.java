package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.UserStatus;
import jdk.jfr.Name;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Name("Tests for UserRepository")
@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

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
}
