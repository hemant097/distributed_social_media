package com.example.linkedInProject.post_service.event;

import lombok.Builder;
import lombok.Data;

@Data
public class PostLikedEvent {

    private Long postId;
    private Long ownerUserId;
    private Long likedByUserId;

}
