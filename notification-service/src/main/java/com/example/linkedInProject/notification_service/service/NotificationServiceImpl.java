package com.example.linkedInProject.notification_service.service;

import com.example.linkedInProject.notification_service.entity.Notification;
import com.example.linkedInProject.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
