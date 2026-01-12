package com.backend.todo.exception;

import java.time.LocalDateTime;


public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String path;
    private String message;
    

    public ErrorResponse(LocalDateTime timestamp, int status,
                         String message, String path) {
    this.timestamp = timestamp;
    this.status = status;
    this.message = message;
    this.path = path;
    }

    // getters
    public LocalDateTime getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public String getPath() { return path; }
    
}
