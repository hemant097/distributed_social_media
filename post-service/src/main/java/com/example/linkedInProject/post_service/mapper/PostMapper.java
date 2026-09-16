package com.example.linkedInProject.post_service.mapper;

import com.example.linkedInProject.post_service.dto.PostCreateRequestDto;
import com.example.linkedInProject.post_service.dto.PostDto;
import com.example.linkedInProject.post_service.entity.Post;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostDto toPostDto(Post post);

    Post toPost(PostCreateRequestDto postCreateRequestDto);

    List<PostDto> toListOfPostDto(List<Post> posts);
}
