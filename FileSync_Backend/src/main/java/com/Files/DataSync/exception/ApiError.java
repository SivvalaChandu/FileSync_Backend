package com.Files.DataSync.exception;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
public class ApiError {
    private String message;
    private LocalDateTime timestamp;

    public ApiError(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
