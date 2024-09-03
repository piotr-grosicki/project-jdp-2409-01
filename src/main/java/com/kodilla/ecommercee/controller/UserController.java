package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("v1/users")
@Tag(name = "Users", description = "Managing users")
public class UserController {
    @Operation(
            description = "Create user in database",
            summary = "Create user"
    )
    @PostMapping
    public void createUser(@RequestBody UserDto userDto) {

    }
    @Operation(
            description = "Update user status",
            summary = "Change status"
    )

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
    @Operation(
            description = "Create temporary key for user",
            summary = "Create key"
    )
    @PostMapping("/key")
    public int temporaryKeyGen(@RequestParam String username, @RequestParam String password) {
            return 1;
        }
}
