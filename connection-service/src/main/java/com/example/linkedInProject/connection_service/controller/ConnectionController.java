package com.example.linkedInProject.connection_service.controller;

import com.example.linkedInProject.connection_service.entity.Person;
import com.example.linkedInProject.connection_service.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionService connectionService;

    @GetMapping("/{userid}/first-degree")
    public ResponseEntity<List<Person>> getFirstDegreeConnections(@PathVariable("userid") Long userId){
        return ResponseEntity.ok(connectionService.getFirstDegreeConnections(userId));
    }

}
