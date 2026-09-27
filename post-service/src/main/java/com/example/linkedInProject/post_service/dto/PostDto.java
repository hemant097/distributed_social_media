package com.example.linkedInProject.post_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {

//    private Long id;

    private String content;

    private Long userId;

    @JsonFormat(pattern = "hh:mm:ss a dd-MMM-YYYY")
    private LocalDateTime createdAt;

    private String fileUrl;
}
