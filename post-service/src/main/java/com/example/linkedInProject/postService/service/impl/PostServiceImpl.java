package com.example.linkedInProject.postService.service.impl;

import com.example.linkedInProject.postService.client.ConnectionServiceClient;
import com.example.linkedInProject.postService.dto.PersonDto;
import com.example.linkedInProject.postService.dto.PostCreateRequestDto;
import com.example.linkedInProject.postService.dto.PostDto;
import com.example.linkedInProject.postService.entity.Post;
import com.example.linkedInProject.postService.event.PostCreatedEvent;
import com.example.linkedInProject.postService.exception.ResourceNotFoundException;
import com.example.linkedInProject.postService.mapper.PostMapper;
import com.example.linkedInProject.postService.repository.PostRepository;
import com.example.linkedInProject.postService.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepo;
    private final PostMapper postMapper;
    private final ConnectionServiceClient connectionClient;
    private final KafkaTemplate<Long, PostCreatedEvent> postCreatedKafkaTemplate;

    @Override
    public PostDto createPost(PostCreateRequestDto postCreateRequestDto,Long userId) {
        log.info("Creating post for user with id: {}",userId);
        Post post = postMapper.toPost(postCreateRequestDto);
        post.setUserId(userId);
        post = postRepo.save(post);

        List<PersonDto> personDtoList = connectionClient.getFirstDegreeConnections(userId);



        //sending notification to all the first degree connections
        for( PersonDto personDto : personDtoList){
            PostCreatedEvent postCreatedEvent = PostCreatedEvent.builder()
                    .postId(post.getId())
                    .content(post.getContent())
                    .userId(personDto.getUserId())
                    .ownerUserId(userId)
                    .build();
            postCreatedKafkaTemplate.send("post-created-topic",postCreatedEvent);
        }

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
