package com.example.linkedInProject.connection_service.event;

import lombok.Data;

@Data
public class ConnectionRequestEvent {

    private Long senderId, receiverId;
    private RequestStatus requestStatus;

}
