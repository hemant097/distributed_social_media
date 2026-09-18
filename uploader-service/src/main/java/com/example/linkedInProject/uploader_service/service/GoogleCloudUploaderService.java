package com.example.linkedInProject.uploader_service.service;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleCloudUploaderService implements UploaderService {

    @Value("${gcloud.storage-bucket-name}")
    private String bucketName;

    private final Storage googleCloudStorage;

    @Override
    public String uploadFile(MultipartFile file) {
        log.info("Trying to upload a file named: {}, with approx. size : {} KiB",file.getOriginalFilename(), file.getSize()/1024);
        String fileName = UUID.randomUUID()+"-"+file.getOriginalFilename().replace(" ","_");
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName,fileName).build();

        try {
            googleCloudStorage.create(blobInfo,file.getBytes());
        }catch (IOException ie){
            throw new RuntimeException(ie);
        }

        return String.format("https://storage.googleapis.com/%s/%s",bucketName,fileName);

    }
}
