package com.example.linkedInProject.connection_service.service;

import com.example.linkedInProject.connection_service.entity.Person;
import com.example.linkedInProject.connection_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService{

    private final PersonRepository personRepo;

    @Override
    public void createPersonNode(Long userId, String name) {
        Person person =  Person.builder()
                .name(name)
                .userId(userId)
                .build();
        personRepo.save(person);
        //save a new node in neo4j database

    }
}
