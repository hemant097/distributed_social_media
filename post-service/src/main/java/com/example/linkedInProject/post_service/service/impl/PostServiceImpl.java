package com.example.linkedInProject.post_service.service.impl;

import com.example.linkedInProject.post_service.auth.AuthContextHolder;
import com.example.linkedInProject.post_service.client.ConnectionServiceClient;
import com.example.linkedInProject.post_service.client.UploaderServiceClient;
import com.example.linkedInProject.post_service.dto.PersonDto;
import com.example.linkedInProject.post_service.dto.PostCreateRequestDto;
import com.example.linkedInProject.post_service.dto.PostDto;
import com.example.linkedInProject.post_service.entity.Post;
import com.example.linkedInProject.post_service.event.PostCreatedEvent;
import com.example.linkedInProject.post_service.exception.ResourceNotFoundException;
import com.example.linkedInProject.post_service.mapper.PostMapper;
import com.example.linkedInProject.post_service.repository.PostRepository;
import com.example.linkedInProject.post_service.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepo;
    private final PostMapper postMapper;
    private final ConnectionServiceClient connectionClient;
    private final KafkaTemplate<Long, PostCreatedEvent> postCreatedKafkaTemplate;
    private final UploaderServiceClient uploaderServiceClient;

    @Override
    public PostDto createPost(PostCreateRequestDto postCreateRequestDto, MultipartFile fileToUpload) {
        Long userId = AuthContextHolder.getCurrentUserId();
        log.info("Creating post for user with id: {}",userId);

        ResponseEntity<String> imageUrl = uploaderServiceClient.uploadFile(fileToUpload);

        Post post = postMapper.toPost(postCreateRequestDto);
        post.setUserId(userId);
        post.setImageUrl(imageUrl.getBody());
        post = postRepo.save(post);

        List<PersonDto> personDtoList = connectionClient.getFirstDegreeConnections(userId);


        //sending notification to all the first degree connections, with first 10 characters of the post message
        for( PersonDto personDto : personDtoList){
            PostCreatedEvent postCreatedEvent = PostCreatedEvent.builder()
                    .postId(post.getId())
                    .content(post.getContent().substring(0,10) + " ...")
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
