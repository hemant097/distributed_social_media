package com.example.linkedInProject.notification_service.service;

import com.example.linkedInProject.notification_service.dto.NotificationDto;
import com.example.linkedInProject.notification_service.entity.Notification;

import java.util.List;

public interface NotificationService {

    void addNotification(Notification notification);

    List<NotificationDto> getUserNotifications();
}
