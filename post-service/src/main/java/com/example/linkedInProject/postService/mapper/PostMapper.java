package com.example.linkedInProject.postService.mapper;

import com.example.linkedInProject.postService.dto.PostCreateRequestDto;
import com.example.linkedInProject.postService.dto.PostDto;
import com.example.linkedInProject.postService.entity.Post;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostDto toPostDto(Post post);

    Post toPost(PostCreateRequestDto postCreateRequestDto);

    List<PostDto> toListOfPostDto(List<Post> posts);
}
