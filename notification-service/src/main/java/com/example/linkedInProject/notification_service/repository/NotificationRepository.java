package com.example.linkedInProject.notification_service.repository;

import com.example.linkedInProject.notification_service.dto.NotificationDto;
import com.example.linkedInProject.notification_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    @Query("Select n.message as message, n.createdAt as receivedAt from Notification n where n.userId=:userId")
    List<NotificationDto> findNotificationById(@Param("userId") Long id);

}
