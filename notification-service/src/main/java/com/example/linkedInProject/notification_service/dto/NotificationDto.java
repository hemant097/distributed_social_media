package com.example.linkedInProject.notification_service.dto;

import java.time.LocalDateTime;

public interface NotificationDto{
    String getMessage();
    LocalDateTime getReceivedAt();
}
