package com.ismoilov.cloud_file_storage.service;

import com.ismoilov.cloud_file_storage.config.MinioConfig;
import io.minio.*;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MinioService {
    private final MinioClient minioClient;
    private final MinioConfig minioConfig;

    public void uploadFile(MultipartFile file, String fileName) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file: " + fileName, e);
        }

    }

    public List<String> listFiles(String prefix) {
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs
                            .builder()
                            .bucket(minioConfig.getBucketName())
                            .prefix(prefix)
                            .build()
            );
            List<String> fileNames = new ArrayList<>();
            for (Result<Item> result : results) {
                Item item = result.get();
                fileNames.add(item.objectName());
            }
            return fileNames;
        } catch (Exception e) {
            throw new RuntimeException("Failed to list objects", e);
        }
    }

    public void deleteFile(String fileName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(fileName)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete file: " + fileName, e);
        }
    }
    public InputStream downloadFile(String fileName) {
        try{
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(fileName)
                            .build()
            );

        }catch (Exception e){
            throw new RuntimeException("Failed to download file: " + fileName, e);
        }
    }
    public void createFolder( String folderName){
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(folderName+"/")
                            .stream(new ByteArrayInputStream(new byte[]{}), 0, -1)
                            .build()

            );
        }catch (Exception e){
            throw new RuntimeException("Failed to create folder: " + folderName, e);
        }
    }

    public void moveFile(String sourcePath, String targetPath){
        try {
            minioClient.copyObject(
                    CopyObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(targetPath)
                            .source(CopySource.builder()
                                            .bucket(minioConfig.getBucketName())
                                            .object(sourcePath)
                                            .build())

                            .build()
            );
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(sourcePath)
                            .build()
            );

        }catch (Exception e){
            throw new RuntimeException("Failed to move file: " + sourcePath, e);
        }
    }

    public List<String> searchFile(String query){
        List<String> fileNames = new ArrayList<>();
        for(String fileName : listFiles("")){
            if(fileName.contains(query)) {
                fileNames.add(fileName);
            }
        }
        return fileNames;

    }

}
