package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.CreateUserDto;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User mapCreateUserDtoToUser(CreateUserDto createUserDto) {
        return new User(
                null,
                createUserDto.getUsername(),
                createUserDto.getFirstName(),
                createUserDto.getLastName(),
                createUserDto.getEmail(),
                createUserDto.getPassword(),
                null,
                null,
                null,
                null,
                null
        );
    }

    public UserDto mapUserToUserDto(User user) {
        return new UserDto(
                user.getUserId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getStatus(),
                user.getTemporaryKey(),
                user.getCreateDate()
        );
    }
}
