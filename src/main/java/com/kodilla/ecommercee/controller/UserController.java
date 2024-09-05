package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.CartDto;
import com.kodilla.ecommercee.domain.UpdateGroupDto;
import com.kodilla.ecommercee.domain.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser() {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            description = "Update user status",
            summary = "Change status"
    )

    @PutMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
@Operation(
            description = "Create temporary key for user",
            summary = "Create key"
    )
    @PostMapping(value = "/key/{userId}")
    public int temporaryKeyGen(@PathVariable Long userId) {
            return 1;
        }
}
