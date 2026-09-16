package com.example.linkedInProject.postService.service;

import com.example.linkedInProject.postService.dto.PostCreateRequestDto;
import com.example.linkedInProject.postService.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostCreateRequestDto postCreateRequestDto,Long userId);

    PostDto getPostById(Long postId);

    List<PostDto> getAllPostsOfUser(Long userId);
}
