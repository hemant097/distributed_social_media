package com.example.linkedInProject.post_service.service;

import com.example.linkedInProject.post_service.dto.PostCreateRequestDto;
import com.example.linkedInProject.post_service.dto.PostDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    PostDto createPost(PostCreateRequestDto postCreateRequestDto, MultipartFile multipartFile);

    PostDto getPostById(Long postId);

    List<PostDto> getAllPostsOfUser(Long userId);
}
