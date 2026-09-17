package com.example.linkedInProject.user_service.event;

import lombok.Data;

@Data
public class UserCreatedEvent {
    private Long userId;
    private String name;
}
