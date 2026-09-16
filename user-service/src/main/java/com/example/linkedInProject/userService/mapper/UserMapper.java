package com.example.linkedInProject.userService.mapper;

import com.example.linkedInProject.userService.dto.LoginRequestDto;
import com.example.linkedInProject.userService.dto.SignupRequestDto;
import com.example.linkedInProject.userService.dto.UserDto;
import com.example.linkedInProject.userService.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(SignupRequestDto signupRequestDto);

    User toUser(LoginRequestDto loginRequestDto);

    UserDto toUserDto(User user);
}
