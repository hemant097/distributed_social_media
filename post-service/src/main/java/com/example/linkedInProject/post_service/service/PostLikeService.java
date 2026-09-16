package com.example.linkedInProject.post_service.service;

public interface PostLikeService {
    void unlikePost(Long postId);

    void likePost(Long postId);
}
