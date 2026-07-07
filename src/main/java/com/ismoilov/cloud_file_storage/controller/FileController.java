package com.ismoilov.cloud_file_storage.controller;

import com.ismoilov.cloud_file_storage.service.MinioService;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {
    private final MinioService minioService;

    @PostMapping("/upload")
    public ResponseEntity<Void> upload(@RequestParam("file") MultipartFile file, @RequestParam("path") String fileName) {
        minioService.uploadFile(file, fileName);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<String>> listFiles(@RequestParam(value = "path", required = false, defaultValue = "") String prefix) {
        return ResponseEntity.ok().body(minioService.listFiles(prefix));

    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteFile(@RequestParam(value = "path") String fileName) {
        minioService.deleteFile(fileName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam(value = "path") String fileName) {
        InputStream inputStream = minioService.downloadFile(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(new InputStreamResource(inputStream));
    }

    @PostMapping("/folder")
    public ResponseEntity<Void> createFolder(@RequestParam(value = "path") String folderName) {
        minioService.createFolder(folderName);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/move")
    public ResponseEntity<Void> move(@RequestParam(value = "source") String source, @RequestParam(value = "target") String target) {
        minioService.moveFile(source, target);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<String>> search(@RequestParam(value = "query") String query) {
        return ResponseEntity.ok().body(minioService.searchFile(query));
    }
}
