package com.example.linkedInProject.connection_service.consumer_kafka;

import com.example.linkedInProject.connection_service.service.PersonService;
import com.example.linkedInProject.user_service.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceConsumer {

    private final PersonService personService;

    //consume the event sent by user-service, that a new user has signed up
    @KafkaListener(topics = "user-created-topic")
    public void handlePersonCreated(UserCreatedEvent userCreatedEvent){
        log.info("handlePersonCreated: {}",userCreatedEvent);
        personService.createPersonNode(userCreatedEvent.getUserId(),userCreatedEvent.getName());
    }


}
