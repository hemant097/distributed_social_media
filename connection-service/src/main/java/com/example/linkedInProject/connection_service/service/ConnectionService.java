package com.example.linkedInProject.connection_service.service;

import com.example.linkedInProject.connection_service.dto.PersonDto;
import com.example.linkedInProject.connection_service.entity.Person;

import java.util.List;

public interface ConnectionService {

    List<PersonDto> getFirstDegreeConnections(Long userId);

    List<PersonDto> getSecondDegreeConnections(Long userId);

    List<PersonDto> getThirdDegreeConnections(Long userId);

    void sendConnectionRequest(Long toUserId);

    void acceptConnectionRequest(Long senderId);

    void rejectConnectionRequest(Long senderId);
    }
