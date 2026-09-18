package com.example.linkedInProject.uploader_service.service;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

//@Service
@Slf4j
@RequiredArgsConstructor
public class CloudinaryUploaderService implements UploaderService{

    private final Cloudinary cloudinary;

/*   MultipartFile represents a file uploaded by the client to Spring application.
    It is mainly used when the HTTP request has multipart/form-data.
    It is spring's convenient object for handling a file received through an HTTP file upload.
 */
    @Override
    public String uploadFile(MultipartFile file) {
        log.info("Trying to upload a file named: {}, with approx. size : {} KiB",file.getOriginalFilename(), file.getSize()/1024);
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), Map.of());


            log.info("successfully uploaded the file");
            return uploadResult.get("secure_url").toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
