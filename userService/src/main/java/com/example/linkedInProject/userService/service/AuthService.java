package com.example.linkedInProject.userService.service;

import com.example.linkedInProject.userService.dto.LoginRequestDto;
import com.example.linkedInProject.userService.dto.SignupRequestDto;
import com.example.linkedInProject.userService.dto.UserDto;

public interface AuthService {
    UserDto signUp(SignupRequestDto signupRequest);

    String login(LoginRequestDto loginRequest);
}
