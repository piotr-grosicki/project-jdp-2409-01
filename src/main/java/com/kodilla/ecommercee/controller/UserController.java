package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.UserDto;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("v1/users")
public class UserController {

    @PostMapping
    public void createUser(@RequestBody UserDto userDto) {

    }
    @PutMapping("/{id}")
    public UserDto updateUserStatus(@PathVariable Long id, @RequestBody UserDto userDto) {
        return new UserDto(
                1L,
                "TestUser",
                "Test",
                "User",
                "testuser@gmail.com",
                "test123",
                "newStatus",
                123,
                LocalDate.of(2024,9,2)
        );
    }

    @PostMapping("/key")
    public int temporaryKeyGen(@RequestParam String username, @RequestParam String password) {
            return 1;
        }
}
