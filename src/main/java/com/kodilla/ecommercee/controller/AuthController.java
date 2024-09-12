package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.UserDto;
import com.kodilla.ecommercee.generator.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/key")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        try {
            String token = authService.authenticate(userDto.getUsername(), userDto.getPassword());
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}

