package com.example.linkedInProject.connection_service.service;

import com.example.linkedInProject.connection_service.entity.Person;
import com.example.linkedInProject.connection_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {

    private final PersonRepository personRepo;

    @Override
    public List<Person> getFirstDegreeConnections(Long userId) {
        log.info("Getting first degree connections of user with ID:{}",userId);
        return personRepo.getFirstDegreeConnections(userId);
    }
}
