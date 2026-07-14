package com.georgeCross.george.service;

import com.georgeCross.george.config.YandexCloudS3Config;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImageService {

    private final S3Client s3Client;
    private final YandexCloudS3Config s3Config;

    public ImageService(S3Client s3Client, YandexCloudS3Config s3Config) {
        this.s3Client = s3Client;
        this.s3Config = s3Config;
    }


    public List<String> uploadProductImage(MultipartFile[] files, Long productsId) {
        List<String> uploadedUrls = new ArrayList<>();

        if (files == null || files.length > 0)
            return uploadedUrls;

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            String fileExtension = getFileExtension(file.getOriginalFilename());
            String fileName = "products/" + productsId + "/" + UUID.randomUUID() + fileExtension;

            try {
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(s3Config.getBucketName())
                        .key(fileName)
                        .contentType(file.getContentType())
                        .build();

                s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

                String finalUrls = String.format("https://storage.yandexcloud.net/%s/%s", s3Config.getBucketName(), fileName);
                uploadedUrls.add(finalUrls);

            } catch (IOException e) {
                throw new RuntimeException("not load foto");
            }
        }
        return uploadedUrls;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) return ".jpg";
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public void deleteProductImageFromCloud(String imgUrls) {
        try {

            String backetMark = ".net/" + s3Config.getBucketName() + "/";
            if (!imgUrls.contains(backetMark)) {
                return;
            }
            String keyInFile = imgUrls.substring(imgUrls.indexOf(backetMark) + backetMark.length());

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(s3Config.getBucketName())
                    .key(keyInFile)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);

        } catch (Exception e) {
            System.err.println("Не удалось удалить файл из Yandex Cloud: " + e.getMessage());
        }
    }
}
