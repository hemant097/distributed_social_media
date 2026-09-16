package com.example.linkedInProject.post_service.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostCreatedEvent {
    private Long ownerUserId; //post owner
    private Long postId;
    private Long userId; //intended user for notification
    private String content;
}
