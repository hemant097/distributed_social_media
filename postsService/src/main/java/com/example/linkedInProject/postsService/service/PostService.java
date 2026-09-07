package com.example.linkedInProject.postsService.service;

import com.example.linkedInProject.postsService.dto.PostCreateRequestDto;
import com.example.linkedInProject.postsService.dto.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PostService {
    PostDto createPost(PostCreateRequestDto postCreateRequestDto,Long userId);

    PostDto getPostById(Long postId);

    List<PostDto> getAllPostsOfUser(Long userId);
}
