package com.ismoilov.cloud_file_storage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UsernameAlreadyExistsExcaption.class)
    public ResponseEntity<String> handleUsernameAlreadyExistsExcaption(UsernameAlreadyExistsExcaption e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<String> handleFileNotFoundException(FileNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(StorageException.class)
    public ResponseEntity<String> handleStorageException(StorageException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}
