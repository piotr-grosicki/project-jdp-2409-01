package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User mapCreateUserDtoToUser(UserDto userDto) {
        return new User(
                null,
                userDto.getUsername(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getPassword(),
                null,
                null

        );
    }

    public UserDto mapUserToUserDto(User user) {
        return new UserDto(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword()
        );
    }
}
