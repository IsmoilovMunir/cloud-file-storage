package com.ismoilov.cloud_file_storage.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FileDto {
    private Long id;
    private String fileName;
    private String filePath;
    private String fileSize;
    private TypeFile typeFile;
    private Date lastModified;

}
