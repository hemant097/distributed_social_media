package com.example.linkedInProject.notification_service.consumer_kafka;

import com.example.linkedInProject.notification_service.entity.Notification;
import com.example.linkedInProject.notification_service.service.NotificationService;
import com.example.linkedInProject.post_service.event.PostCreatedEvent;
import com.example.linkedInProject.post_service.event.PostLikedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "post-created-topic")
    public void handlePostCreated(PostCreatedEvent postCreatedEvent){
        log.info("handle post created : {}",postCreatedEvent);

        String message = String.format("Your connection with id: %d has created a new post: %s",postCreatedEvent.getOwnerUserId(),postCreatedEvent.getContent());

        Notification notification = Notification.builder()
                .message(message)
                .userId(postCreatedEvent.getUserId())
                .build();

        notificationService.addNotification(notification);

    }

    @KafkaListener(topics = "post-liked-topic")
    public void handlePostLiked(PostLikedEvent postLikedEvent){
        log.info("handling post liked : {}",postLikedEvent);

        String message = String.format("User with id: %d has liked this post with id: %d",postLikedEvent.getLikedByUserId(),postLikedEvent.getPostId());

        Notification notification = Notification.builder()
                .message(message)
                .userId(postLikedEvent.getOwnerUserId())
                .build();

        notificationService.addNotification(notification);

    }
}
