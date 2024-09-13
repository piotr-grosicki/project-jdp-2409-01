package com.kodilla.ecommercee.generator;

import com.kodilla.ecommercee.controller.exception.InvalidCredentialsException;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public String authenticate(String username, String password) throws InvalidCredentialsException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return jwtService.generateToken(username);
        } else {
            throw new InvalidCredentialsException();
        }
    }
}