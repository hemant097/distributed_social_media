package com.example.linkedInProject.connection_service.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConnectionRequestEvent {

    private Long senderId, receiverId;
    private RequestStatus requestStatus;

}
