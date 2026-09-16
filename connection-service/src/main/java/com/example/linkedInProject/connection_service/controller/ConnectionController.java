package com.example.linkedInProject.connection_service.controller;

import com.example.linkedInProject.connection_service.entity.Person;
import com.example.linkedInProject.connection_service.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
public class ConnectionController {

    private final ConnectionService connectionService;

    @GetMapping("/{userid}/first-degree")
    public ResponseEntity<List<Person>> getFirstDegreeConnections(@PathVariable("userid") Long userId,
                                                                  @RequestHeader("X-User-Id") Long userIdFromHeader){
        log.info("User id is: {}",userId);
        log.info("User id from request headers is: {}",userIdFromHeader);
        return ResponseEntity.ok(connectionService.getFirstDegreeConnections(userId));
    }

    @GetMapping("/{userid}/second-degree")
    public ResponseEntity<List<Person>> getSecondDegreeConnections(@PathVariable("userid") Long userId){
        log.info("User id is: {}",userId);
        return ResponseEntity.ok(connectionService.getSecondDegreeConnections(userId));
    }

    @GetMapping("/{userid}/third-degree")
    public ResponseEntity<List<Person>> getThirdDegreeConnections(@PathVariable("userid") Long userId){
        log.info("User id is: {}",userId);
        return ResponseEntity.ok(connectionService.getThirdDegreeConnections(userId));
    }

}
