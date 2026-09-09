package com.example.linkedInProject.userService.controller;

import com.example.linkedInProject.userService.dto.LoginRequestDto;
import com.example.linkedInProject.userService.dto.SignupRequestDto;
import com.example.linkedInProject.userService.dto.UserDto;
import com.example.linkedInProject.userService.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUp(@RequestBody SignupRequestDto signupRequest){
        UserDto userDto = authService.signUp(signupRequest);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequest){
        String token = authService.login(loginRequest);
        return ResponseEntity.ok(token);
    }

}
