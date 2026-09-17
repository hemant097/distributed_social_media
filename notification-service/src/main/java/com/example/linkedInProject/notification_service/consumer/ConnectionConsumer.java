package com.example.linkedInProject.notification_service.consumer;

import com.example.linkedInProject.connection_service.event.ConnectionRequestEvent;
import com.example.linkedInProject.connection_service.event.RequestStatus;
import com.example.linkedInProject.notification_service.entity.Notification;
import com.example.linkedInProject.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "connection-request-topic")
    public void handlerConnectionRequest(ConnectionRequestEvent connectionRequestEvent){
        log.info("handle connection request event : {}",connectionRequestEvent);

        //sending notification to receiver of the connection request
        if(connectionRequestEvent.getRequestStatus().equals(RequestStatus.SENT)){
            String message = String.format("Your have received a connection request from id: %d ",connectionRequestEvent.getSenderId());

            createAndSaveNotification (message, connectionRequestEvent.getReceiverId());
        }

        //sending notification to sender of the connection request
        if(connectionRequestEvent.getRequestStatus().equals(RequestStatus.ACCEPTED)){
            String message = String.format("Your connection request to: %d has been accepted",connectionRequestEvent.getReceiverId());

            createAndSaveNotification (message, connectionRequestEvent.getSenderId());
        }

        //sending notification to sender of the connection request
        if(connectionRequestEvent.getRequestStatus().equals(RequestStatus.REJECTED)){
            String message = String.format("Your connection request to: %d has been rejected",connectionRequestEvent.getReceiverId());

            createAndSaveNotification (message, connectionRequestEvent.getSenderId());
        }


    }

    void createAndSaveNotification(String message, Long userId){
        Notification notification = Notification.builder()
                .message(message)
                .userId(userId)
                .build();
        notificationService.addNotification(notification);
    }


}
