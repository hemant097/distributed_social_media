package com.example.linkedInProject.postsService.service.impl;

import com.example.linkedInProject.postsService.entity.PostLike;
import com.example.linkedInProject.postsService.exception.BadRequestException;
import com.example.linkedInProject.postsService.exception.ResourceNotFoundException;
import com.example.linkedInProject.postsService.repository.PostLikeRepository;
import com.example.linkedInProject.postsService.repository.PostRepository;
import com.example.linkedInProject.postsService.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepo;
    private final PostRepository postRepo;


    @Override
    public void likePost(Long postId) {
        Long userId = 1L;
        log.info("User with ID:{}, Liking a post with id:{}",userId,postId);

        checkIfPostExists(postId);

        boolean hasAlreadyLiked = postLikeRepo.existsByUserIdAndPostId(userId,postId);

        if(hasAlreadyLiked)
            throw new BadRequestException("Cannot like the post again");

        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeRepo.save(postLike);

        //TODO: send notification to the owner of the post


    }

    @Override
    @Transactional //as delete operation
    public void unlikePost(Long postId) {
        Long userId = 1L;

        log.info("UnLiking a post with id:{}",postId);

        checkIfPostExists(postId);

        boolean hasLiked = postLikeRepo.existsByUserIdAndPostId(userId,postId);

        if(!hasLiked)
            throw new BadRequestException("Like this post first to unlike");

        postLikeRepo.deleteByUserIdAndPostId(postId,userId);

    }

    void checkIfPostExists(Long postId){
        postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post not found with ID: " + postId));
    }


}
