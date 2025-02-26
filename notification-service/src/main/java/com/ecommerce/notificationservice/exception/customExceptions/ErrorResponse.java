package com.ecommerce.notificationservice.exception.customExceptions;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private int status;
    private String error;
    private String details;
    private String path;


}