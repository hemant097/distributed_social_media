package com.example.linkedInProject.post_service.client;

import com.example.linkedInProject.post_service.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "connection-service", path = "/connections")
public interface ConnectionServiceClient {

    @GetMapping("/core/{userid}/first-degree")
    List<PersonDto> getFirstDegreeConnections(@PathVariable("userid") Long userId);
}
