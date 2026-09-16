package com.example.linkedInProject.user_service.mapper;

import com.example.linkedInProject.user_service.dto.LoginRequestDto;
import com.example.linkedInProject.user_service.dto.SignupRequestDto;
import com.example.linkedInProject.user_service.dto.UserDto;
import com.example.linkedInProject.user_service.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(SignupRequestDto signupRequestDto);

    User toUser(LoginRequestDto loginRequestDto);

    UserDto toUserDto(User user);
}
