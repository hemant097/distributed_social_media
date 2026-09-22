package com.example.linkedInProject.notification_service.service;

import com.example.linkedInProject.notification_service.auth.AuthContextHolder;
import com.example.linkedInProject.notification_service.dto.NotificationDto;
import com.example.linkedInProject.notification_service.entity.Notification;
import com.example.linkedInProject.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepo;

    @Override
    public void addNotification(Notification notification) {
        log.info("Adding notification to db, content: {}",notification.getMessage());
        notificationRepo.save(notification);

    }

    @Override
    public List<NotificationDto> getUserNotifications() {
        Long userId = AuthContextHolder.getCurrentUserId();
        log.info("getting all the notifications of user with id: {}",userId);
        return notificationRepo.findNotificationById(userId);
    }
}
