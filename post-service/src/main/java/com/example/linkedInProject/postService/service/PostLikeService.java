package com.example.linkedInProject.postService.service;

public interface PostLikeService {
    void unlikePost(Long postId);

    void likePost(Long postId);
}
