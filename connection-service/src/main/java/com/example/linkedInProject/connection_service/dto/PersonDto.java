package com.example.linkedInProject.connection_service.dto;

import com.example.linkedInProject.connection_service.entity.Person;

public record PersonDto(
        Long userId,
        String name
) {
}
