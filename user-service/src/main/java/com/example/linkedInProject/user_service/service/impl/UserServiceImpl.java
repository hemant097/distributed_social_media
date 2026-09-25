package com.example.linkedInProject.user_service.service.impl;

import com.example.linkedInProject.user_service.dto.LoginRequestDto;
import com.example.linkedInProject.user_service.dto.SignupRequestDto;
import com.example.linkedInProject.user_service.dto.UserDto;
import com.example.linkedInProject.user_service.entity.User;
import com.example.linkedInProject.user_service.event.UserCreatedEvent;
import com.example.linkedInProject.user_service.exceptions.BadRequestException;
import com.example.linkedInProject.user_service.mapper.UserMapper;
import com.example.linkedInProject.user_service.repository.UserRepository;
import com.example.linkedInProject.user_service.service.AuthService;
import com.example.linkedInProject.user_service.service.JWTService;
import com.example.linkedInProject.user_service.util.BCryptPasswordHasher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final UserMapper userMapper;
    private final JWTService jwtService;
    private final KafkaTemplate<Long,UserCreatedEvent> userCreatedKafkaTemplate;

    @Override
    public UserDto signUp(SignupRequestDto signupRequest) {
        log.info("Signing up user with email:{}",signupRequest.getEmail());

        if(checkIfUserExists(signupRequest.getEmail()))
            throw new BadRequestException("user already exists");

        User user = userMapper.toUser(signupRequest);

        user.setPassword(BCryptPasswordHasher.hashString(signupRequest.getPassword()));
        user = userRepo.save(user);

        UserCreatedEvent userCreatedEvent = UserCreatedEvent.builder()
                .userId(user.getId())
                .name(user.getName())
                .build();

        //send an event, when a new user has signed up, which is to be consumed by connection-service
        userCreatedKafkaTemplate.send("user-created-topic",userCreatedEvent);

        return userMapper.toUserDto(user);
    }

    @Override
    public String login(LoginRequestDto loginRequest) {
        log.info("Logging in user with email:{}",loginRequest.getEmail());

        User user = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BadRequestException("Incorrect credentials"));

        boolean doesPasswordMatch = BCryptPasswordHasher.match(loginRequest.getPassword(), user.getPassword()); //user entity stores hashed pw in db

        if(!doesPasswordMatch)
            throw new BadRequestException("Incorrect credentials");

        log.info("Login successful with email: {}",loginRequest.getEmail());
        return jwtService.generateAccessToken(user);
    }

    boolean checkIfUserExists(String email){
        return userRepo.existsByEmail(email);
    }
}
