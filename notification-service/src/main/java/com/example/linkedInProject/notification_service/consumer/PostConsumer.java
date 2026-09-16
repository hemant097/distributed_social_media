package com.example.linkedInProject.notification_service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PostConsumer {

    @KafkaListener(topics = "post-created-topic")
    public void handlePostCreated(){

    }
}
