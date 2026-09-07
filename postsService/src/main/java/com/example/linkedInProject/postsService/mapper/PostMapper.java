package com.example.linkedInProject.postsService.mapper;

import com.example.linkedInProject.postsService.dto.PostCreateRequestDto;
import com.example.linkedInProject.postsService.dto.PostDto;
import com.example.linkedInProject.postsService.entity.Post;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostDto toPostDto(Post post);

    Post toPost(PostCreateRequestDto postCreateRequestDto);

    List<PostDto> toListOfPostDto(List<Post> posts);
}
