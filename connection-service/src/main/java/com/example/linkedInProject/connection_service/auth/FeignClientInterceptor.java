package com.example.linkedInProject.connection_service.auth;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        Long userId = AuthContextHolder.getCurrentUserId();

        if(userId!=null)
            template.header("X-User-Id",userId.toString());

        //now we don't 've to pass the id in method parameters, while calling the Feign client
    }
}
