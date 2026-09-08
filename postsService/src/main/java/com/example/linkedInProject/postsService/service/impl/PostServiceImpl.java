package com.example.linkedInProject.postsService.service.impl;

import com.example.linkedInProject.postsService.dto.PostCreateRequestDto;
import com.example.linkedInProject.postsService.dto.PostDto;
import com.example.linkedInProject.postsService.entity.Post;
import com.example.linkedInProject.postsService.exception.ResourceNotFoundException;
import com.example.linkedInProject.postsService.mapper.PostMapper;
import com.example.linkedInProject.postsService.repository.PostRepository;
import com.example.linkedInProject.postsService.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepo;
    private final PostMapper postMapper;

    @Override
    public PostDto createPost(PostCreateRequestDto postCreateRequestDto,Long userId) {
        log.info("Creating post for user with id: {}",userId);
        Post post = postMapper.toPost(postCreateRequestDto);
        post.setUserId(userId);
        post = postRepo.save(post);

        return postMapper.toPostDto(post);

    }

    @Override
    public PostDto getPostById(Long postId) {
        log.info("Getting post with id: {}",postId);
        Post post = postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post not found with id "+postId));
        return postMapper.toPostDto(post);
    }

    @Override
    public List<PostDto> getAllPostsOfUser(Long userId) {
        log.info("Getting all post for user id: {}",userId);
        List<Post> posts = postRepo.findByUserId(userId);
        return postMapper.toListOfPostDto(posts);
    }
}
