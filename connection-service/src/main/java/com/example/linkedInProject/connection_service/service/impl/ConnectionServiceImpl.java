package com.example.linkedInProject.connection_service.service.impl;

import com.example.linkedInProject.connection_service.auth.AuthContextHolder;
import com.example.linkedInProject.connection_service.dto.PersonDto;
import com.example.linkedInProject.connection_service.event.ConnectionRequestEvent;
import com.example.linkedInProject.connection_service.event.RequestStatus;
import com.example.linkedInProject.connection_service.exceptions.BadRequestException;
import com.example.linkedInProject.connection_service.repository.PersonRepository;
import com.example.linkedInProject.connection_service.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {

    private final PersonRepository personRepo;
    private final KafkaTemplate<Long, ConnectionRequestEvent> requestEventKafkaTemplate;

    @Override
    public List<PersonDto> getFirstDegreeConnections(Long userId) {
        log.info("Getting first degree connections of user with ID:{}",userId);
        return personRepo.getFirstDegreeConnections(userId);
    }

    @Override
    public List<PersonDto> getSecondDegreeConnections(Long userId) {
        log.info("Getting second degree connections of user with ID:{}",userId);
        List<PersonDto> personList =  personRepo.getSecondDegreeConnections(userId);

        log.info("User has {}, 2nd degree connections",personList.size());
        return personList;    }

    @Override
    public List<PersonDto> getThirdDegreeConnections(Long userId) {
        log.info("Getting third degree connections of user with ID:{}",userId);

        List<PersonDto> personList =  personRepo.getThirdDegreeConnections(userId);
        log.info("User has {}, 3rd degree connections",personList.size());

        return personList;
    }

    @Override
    public void sendConnectionRequest(Long receiverId) {
        Long senderId = AuthContextHolder.getCurrentUserId();
        log.info("trying to send the connection request with senderId:{}, receiverId:{}",senderId,receiverId);

        if( senderId.equals(receiverId))
            throw new BadRequestException("Both sender and receiver are same");

        boolean alreadySentConnectionRequest = personRepo.connectionRequestExists(senderId,receiverId);
        if (alreadySentConnectionRequest)
            throw new BadRequestException("You have already send a connection request to this person, cannot send again");

        boolean alreadyConnected = personRepo.alreadyConnected(senderId,receiverId);
        if (alreadyConnected)
            throw new BadRequestException("You are already connected to this person, cannot send request");

        sendResponseToKafka(RequestStatus.SENT, senderId, receiverId);

        log.info("Successfully sent the connection request with senderId:{}, receiverId:{}",senderId,receiverId);
        personRepo.addConnectionRequest(senderId,receiverId);
    }

    @Override
    public void acceptConnectionRequest(Long senderId) {
        Long receiverId = AuthContextHolder.getCurrentUserId();
        log.info("trying to accept connection request with senderId:{}, receiverId:{}",senderId,receiverId);

        if( senderId.equals(receiverId))
            throw new BadRequestException("Both sender and receiver are same");

        boolean alreadyConnected = personRepo.alreadyConnected(senderId,receiverId);
        if (alreadyConnected)
            throw new BadRequestException("You have already connected to this person, cannot accept again");

        boolean hasSentRequest = personRepo.connectionRequestExists(senderId,receiverId);
        if (!hasSentRequest)
            throw new BadRequestException("No connection request exists");

        sendResponseToKafka(RequestStatus.ACCEPTED, senderId, receiverId);

        log.info("Successfully accepted the connection request with senderId:{}, receiverId:{}",senderId,receiverId);
        personRepo.acceptConnectionRequest(senderId,receiverId);
    }

    @Override
    public void rejectConnectionRequest(Long senderId) {
        Long receiverId = AuthContextHolder.getCurrentUserId();
        log.info("trying to reject connection request with senderId:{}, receiverId:{}",senderId,receiverId);

        if( senderId.equals(receiverId))
            throw new BadRequestException("Both sender and receiver are same");

        boolean hasSentConnectionRequest = personRepo.connectionRequestExists(senderId,receiverId);
        if (!hasSentConnectionRequest)
            throw new BadRequestException("No connection request received, cannot reject");

        sendResponseToKafka(RequestStatus.REJECTED, senderId, receiverId);

        log.info("Successfully deleted the connection request with senderId:{}, receiverId:{}",senderId,receiverId);
        personRepo.rejectConnectionRequest(senderId,receiverId);
    }

    void sendResponseToKafka(RequestStatus status, Long senderId, Long receiverId){
         ConnectionRequestEvent connectionRequestEvent = ConnectionRequestEvent.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .requestStatus(status)
                .build();

        requestEventKafkaTemplate.send("connection-request-topic",connectionRequestEvent);

    }


}
