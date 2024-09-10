package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.controller.exception.EmailExistsException;
import com.kodilla.ecommercee.controller.exception.UserNotFoundException;
import com.kodilla.ecommercee.controller.exception.UsernameExistsException;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.UserStatus;
import com.kodilla.ecommercee.generator.KeyGenerator;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserDbService {
    private final UserRepository userRepository;

    public User createUser(User user) throws UsernameExistsException, EmailExistsException {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UsernameExistsException(user.getUsername());
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailExistsException(user.getEmail());
        }

        user.setStatus(UserStatus.ACTIVE);
        user.setTemporaryKey(KeyGenerator.generateKey(null));
        user.setCreateDate(LocalDate.now());

        return userRepository.save(user);
    }

    public void updateUserStatus(Long userId, UserStatus userStatus) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setStatus(userStatus);

        userRepository.save(user);
    }

    public Integer createUserTemporaryKey(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Integer userTemporaryKey = KeyGenerator.generateKey(userId);

        user.setTemporaryKey(userTemporaryKey);

        userRepository.save(user);

        return userTemporaryKey;
    }
}
