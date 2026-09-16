package com.example.linkedInProject.post_service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer  {

    @Autowired
    private MyRequestInterceptor myRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //adding our custom request interceptor to interceptor registry
        registry.addInterceptor(myRequestInterceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
