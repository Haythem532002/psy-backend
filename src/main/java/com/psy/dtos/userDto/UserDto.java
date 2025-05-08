package com.psy.dtos.userDto;

public record UserDto(
        Integer id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String password

) {
}
