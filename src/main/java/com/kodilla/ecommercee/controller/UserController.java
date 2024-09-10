package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.mapper.UserMapper;
import com.kodilla.ecommercee.service.UserDbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/users")
@Tag(name = "Users", description = "Managing users")
public class UserController {
    private final UserDbService userDbService;
    private final UserMapper userMapper;

    @Operation(
            description = "Create user in database",
            summary = "Create user"
    )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserDto createUserDto) throws UsernameExistsException, EmailExistsException {
        User user = userMapper.mapCreateUserDtoToUser(createUserDto);

        User savedUser = userDbService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body((userMapper.mapUserToUserDto(savedUser)));
    }

    @Operation(
            description = "Update user status",
            summary = "Change status"
    )
    @PatchMapping(value = "/{userId}/status")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> updateUserStatus(@PathVariable Long userId, @RequestBody UserStatusDto userStatusDto) throws UserNotFoundException {
        userDbService.updateUserStatus(userId, userStatusDto.getStatus());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(
            description = "Create temporary key for user",
            summary = "Create key"
    )
    @PostMapping(value = "/{userId}/key", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> temporaryKeyGen(@PathVariable Long userId) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userDbService.createUserTemporaryKey(userId));
    }
}
