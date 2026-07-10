package com.ismoilov.cloud_file_storage.service;

import com.ismoilov.cloud_file_storage.config.MinioConfig;
import com.ismoilov.cloud_file_storage.exception.FileNotFoundException;
import com.ismoilov.cloud_file_storage.exception.StorageException;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
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
            throw new StorageException("Failed to upload file: " + fileName);
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
            throw new StorageException("Failed to list objects");
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
            throw new StorageException("Failed to delete file: " + fileName);
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

        }catch (ErrorResponseException e) {
            if (e.errorResponse().code().equals("NoSuchKey")) {
                throw new FileNotFoundException("File not found: " + fileName);
            }
            throw new StorageException("Failed to download file: " + fileName);
        } catch (Exception e) {
            throw new StorageException("Failed to download file: " + fileName);
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
            throw new StorageException("Failed to create folder: " + folderName);
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
            throw new StorageException("Failed to move file: " + sourcePath);
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
