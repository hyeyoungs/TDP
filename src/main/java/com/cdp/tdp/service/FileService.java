package com.cdp.tdp.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    @Value("${cloud.aws.s3.bucket.uri}")
    public String defaultUri;

    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = createFileName(file.getOriginalFilename());
        String url;
        
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            uploadOnS3(fileName, inputStream, objectMetadata);
            url = defaultUri + fileName;
        } catch (IOException ioe) {
            url = null;
        }

        return url;
    }

    private String createFileName(String originalFileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    private void uploadOnS3(String findName, InputStream inputStream, ObjectMetadata objectMetadata) {

        TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(amazonS3Client).build();
        PutObjectRequest request = new PutObjectRequest(bucket, findName, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead);
        Upload upload =  transferManager.upload(request);

        try {
            upload.waitForCompletion();
        } catch (AmazonClientException amazonClientException) {
            log.error(amazonClientException.getMessage());
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}