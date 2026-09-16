package com.example.linkedInProject.userService.service.impl;

import com.example.linkedInProject.userService.dto.LoginRequestDto;
import com.example.linkedInProject.userService.dto.SignupRequestDto;
import com.example.linkedInProject.userService.dto.UserDto;
import com.example.linkedInProject.userService.entity.User;
import com.example.linkedInProject.userService.exceptions.BadRequestException;
import com.example.linkedInProject.userService.mapper.UserMapper;
import com.example.linkedInProject.userService.repository.UserRepository;
import com.example.linkedInProject.userService.service.AuthService;
import com.example.linkedInProject.userService.service.JWTService;
import com.example.linkedInProject.userService.util.BCrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final UserMapper userMapper;
    private final JWTService jwtService;

    @Override
    public UserDto signUp(SignupRequestDto signupRequest) {
        log.info("Signing up user with email:{}",signupRequest.getEmail());

        if(checkIfUserExists(signupRequest.getEmail()))
            throw new BadRequestException("user already exists");

        User user = userMapper.toUser(signupRequest);

        user.setPassword(BCrypt.hashString(signupRequest.getPassword()));
        user = userRepo.save(user);

        return userMapper.toUserDto(user);
    }

    @Override
    public String login(LoginRequestDto loginRequest) {
        log.info("Logging in user with email:{}",loginRequest.getEmail());

        User user = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BadRequestException("Incorrect credentials"));

        boolean doesPasswordMatch = BCrypt.match(loginRequest.getPassword(), user.getPassword()); //user entity stores hashed pw in db

        if(!doesPasswordMatch)
            throw new BadRequestException("Incorrect credentials");

        return jwtService.generateAccessToken(user);
    }

    boolean checkIfUserExists(String email){
        return userRepo.existsByEmail(email);
    }
}
