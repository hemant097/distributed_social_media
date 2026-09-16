package com.example.linkedInProject.user_service.service;

import com.example.linkedInProject.user_service.dto.LoginRequestDto;
import com.example.linkedInProject.user_service.dto.SignupRequestDto;
import com.example.linkedInProject.user_service.dto.UserDto;

public interface AuthService {
    UserDto signUp(SignupRequestDto signupRequest);

    String login(LoginRequestDto loginRequest);
}
