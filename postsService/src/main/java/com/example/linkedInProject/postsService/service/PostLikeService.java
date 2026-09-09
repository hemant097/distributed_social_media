package com.example.linkedInProject.postsService.service;

public interface PostLikeService {
    void unlikePost(Long postId);

    void likePost(Long postId);
}
