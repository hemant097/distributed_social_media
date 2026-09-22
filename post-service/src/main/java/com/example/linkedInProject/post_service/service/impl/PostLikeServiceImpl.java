package com.example.linkedInProject.post_service.service.impl;

import com.example.linkedInProject.post_service.auth.AuthContextHolder;
import com.example.linkedInProject.post_service.entity.Post;
import com.example.linkedInProject.post_service.entity.PostLike;
import com.example.linkedInProject.post_service.event.PostLikedEvent;
import com.example.linkedInProject.post_service.exception.BadRequestException;
import com.example.linkedInProject.post_service.exception.ResourceNotFoundException;
import com.example.linkedInProject.post_service.repository.PostLikeRepository;
import com.example.linkedInProject.post_service.repository.PostRepository;
import com.example.linkedInProject.post_service.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepo;
    private final PostRepository postRepo;
    private final KafkaTemplate<Long, PostLikedEvent> postLikedKafkaTemplate;


    @Override
    public void likePost(Long postId) {
        Long userId = AuthContextHolder.getCurrentUserId();
        log.info("User with ID:{}, Liking a post with id:{}",userId,postId);

        Post post = checkIfPostExists(postId);

        boolean hasAlreadyLiked = postLikeRepo.existsByUserIdAndPostId(userId,postId);

        if(hasAlreadyLiked)
            throw new BadRequestException("Cannot like the post again");

        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeRepo.save(postLike);


        //sending notification to the owner of the post

        PostLikedEvent postLikedEvent = PostLikedEvent.builder()
                .postId(postId)
                .likedByUserId(userId)
                .ownerUserId(post.getUserId())
                .build();

        postLikedKafkaTemplate.send("post-liked-topic",postLikedEvent);



    }

    @Override
    @Transactional //as delete operation
    public void unlikePost(Long postId) {
        Long userId = AuthContextHolder.getCurrentUserId();

        log.info("UnLiking a post with id:{}",postId);

        checkIfPostExists(postId);

        boolean hasLiked = postLikeRepo.existsByUserIdAndPostId(userId,postId);

        if(!hasLiked)
            throw new BadRequestException("Like this post first to unlike");

        postLikeRepo.deleteByUserIdAndPostId(postId,userId);

    }

    Post checkIfPostExists(Long postId){
        return postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post not found with ID: " + postId));
    }


}
