package com.ismoilov.cloud_file_storage.controller;

import com.ismoilov.cloud_file_storage.service.MinioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Tag(name = "Files", description = "File management API")
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {
    private final MinioService minioService;


    @Operation(summary = "Upload file", description = "Upload file to storage")
    @PostMapping("/upload")
    public ResponseEntity<Void> upload(@RequestParam("file") MultipartFile file, @RequestParam("path") String fileName) {
        minioService.uploadFile(file, fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + URLEncoder.encode(fileName, StandardCharsets.UTF_8))
                .build();
    }

    @Operation(summary = "listFiles file", description = "listFiles file to storage")
    @GetMapping()
    public ResponseEntity<List<String>> listFiles(@RequestParam(value = "path", required = false, defaultValue = "") String prefix) {
        return ResponseEntity.ok().body(minioService.listFiles(prefix));

    }

    @Operation(summary = "deleteFile file", description = "deleteFile file to storage")
    @DeleteMapping()
    public ResponseEntity<Void> deleteFile(@RequestParam(value = "path") String fileName) {
        minioService.deleteFile(fileName);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "download file", description = "download file to storage")
    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam(value = "path") String fileName) {
        InputStream inputStream = minioService.downloadFile(fileName);
        String contentType = URLConnection.guessContentTypeFromName(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.parseMediaType(contentType != null ? contentType : "application/octet-stream"))
                .body(new InputStreamResource(inputStream));
    }

    @Operation(summary = "createFolder file", description = "createFolder file to storage")
    @PostMapping("/folder")
    public ResponseEntity<Void> createFolder(@RequestParam(value = "path") String folderName) {
        minioService.createFolder(folderName);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "move file", description = "move file to storage")
    @PostMapping("/move")
    public ResponseEntity<Void> move(@RequestParam(value = "source") String source, @RequestParam(value = "target") String target) {
        minioService.moveFile(source, target);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "search file", description = "search file to storage")
    @GetMapping("/search")
    public ResponseEntity<List<String>> search(@RequestParam(value = "query") String query) {
        return ResponseEntity.ok().body(minioService.searchFile(query));
    }
}
