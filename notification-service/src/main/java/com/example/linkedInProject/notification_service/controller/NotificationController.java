package com.example.linkedInProject.notification_service.controller;

import com.example.linkedInProject.notification_service.dto.NotificationDto;
import com.example.linkedInProject.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    ResponseEntity<List<NotificationDto>> getMyNotifications(){
        return ResponseEntity.ok(notificationService.getUserNotifications());
    }
}
