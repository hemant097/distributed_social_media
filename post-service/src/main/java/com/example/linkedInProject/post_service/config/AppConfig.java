package com.example.linkedInProject.post_service.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;

public class AppConfig {

    @Bean
    public Encoder feignFormEncoder() {
        SpringFormEncoder springFormEncoder = new SpringFormEncoder();
        System.out.println("Feign Encoder: " + springFormEncoder.getClass().getName());
        return springFormEncoder;
    }

}
