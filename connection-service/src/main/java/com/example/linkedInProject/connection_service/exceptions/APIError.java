package com.example.linkedInProject.connection_service.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record APIError(
        LocalDateTime timestamp,
        String error,
        HttpStatus statusCode
) {

    public APIError(String error,HttpStatus statusCode){
        this(LocalDateTime.now(),error,statusCode);
    }

}
