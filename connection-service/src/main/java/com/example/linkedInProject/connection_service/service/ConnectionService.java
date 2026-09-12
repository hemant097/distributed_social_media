package com.example.linkedInProject.connection_service.service;

import com.example.linkedInProject.connection_service.entity.Person;

import java.util.List;

public interface ConnectionService {

    List<Person> getFirstDegreeConnections(Long userId);
}
