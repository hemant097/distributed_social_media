package com.example.linkedInProject.connection_service.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic connectionRequest(){
        return new NewTopic("connection-request-topic",3,(short) 1);
    }


}