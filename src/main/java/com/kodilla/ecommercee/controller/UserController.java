package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.controller.exception.EmailExistsException;
import com.kodilla.ecommercee.controller.exception.UserNotFoundException;
import com.kodilla.ecommercee.controller.exception.UsernameExistsException;
import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.mapper.UserMapper;
import com.kodilla.ecommercee.service.EventDbService;
import com.kodilla.ecommercee.service.EventDetailDbService;
import com.kodilla.ecommercee.service.UserDbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/users")
@Tag(name = "Users", description = "Managing users")
public class UserController {
    private final UserDbService userDbService;
    private final UserMapper userMapper;
    private final EventDbService eventDbService;
    private final EventDetailDbService eventDetailDbService;

    @Operation(
            description = "Registers a new user with the provided username, email, and other details",
            summary = "Create a new user"
    )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserDto createUserDto) throws UsernameExistsException, EmailExistsException {
        User user = userMapper.mapCreateUserDtoToUser(createUserDto);
        User savedUser = userDbService.createUser(user);

        Event event = eventDbService.saveEvent(savedUser.getUserId(), EventTitle.USER_CREATION);
        Map<EventDetailKey, String> eventDetails = new HashMap<>();
        eventDetails.put(EventDetailKey.USER_ID, savedUser.getUserId().toString());
        eventDetailDbService.saveEventDetails(event, eventDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body((userMapper.mapUserToUserDto(savedUser)));
    }

    @Operation(
            description = "Updates the status of an existing user identified by its ID.",
            summary = "Update user status"
    )
    @PatchMapping(value = "/{userId}/status")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> updateUserStatus(@PathVariable Long userId, @RequestBody UserStatusDto userStatusDto) throws UserNotFoundException {
        userDbService.updateUserStatus(userId, userStatusDto.getStatus());

        Event event = eventDbService.saveEvent(userId, EventTitle.USER_STATUS_CHANGE);
        Map<EventDetailKey, String> eventDetails = new HashMap<>();
        eventDetails.put(EventDetailKey.USER_STATUS, userStatusDto.getStatus().toString());
        eventDetailDbService.saveEventDetails(event, eventDetails);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
