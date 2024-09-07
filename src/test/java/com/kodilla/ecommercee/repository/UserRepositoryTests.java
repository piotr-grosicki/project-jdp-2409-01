package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.User;
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
        User user = new User(1L,"CarolD","Carol","Denver", "carold@gmail.com", "Carol123", "Active", 124, LocalDate.of(2024,9,6), null, null);
        User savedUser = userRepository.save(user);
        Long userId = savedUser.getUserId();
        //When
        Optional<User> userOptional = userRepository.findById(userId);
        //Then
        Assertions.assertTrue(userOptional.isPresent());
        Assertions.assertEquals("CarolD", userOptional.get().getUsername());
    }
    @Name("Test for changing status")
    @Test
    public void shouldChangeUserStatus() {
        //Given
        User user = new User(1L,"CarolD","Carol","Denver", "carold@gmail.com", "Carol123", "Active", 124, LocalDate.of(2024,9,6),null,null);
        User savedUser = userRepository.save(user);
        Long userId = savedUser.getUserId();
        //When
        //changin status
        String beforeChange = savedUser.getStatus();
        savedUser.setStatus("Blocked");
        userRepository.save(savedUser);
        String afterChange = savedUser.getStatus();
        //Then
        Assertions.assertEquals("Active", beforeChange);
        Assertions.assertEquals("Blocked", afterChange);
    }
}
