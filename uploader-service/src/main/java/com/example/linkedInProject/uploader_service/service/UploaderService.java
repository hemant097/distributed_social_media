package com.example.linkedInProject.uploader_service.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploaderService {

    String uploadFile(MultipartFile file);
}
