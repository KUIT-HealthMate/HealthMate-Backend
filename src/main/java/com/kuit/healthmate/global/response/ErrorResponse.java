package com.kuit.healthmate.global.response;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class ErrorResponse {
    private final int code;
    private final int status;
    private final String message;
    private final LocalDateTime timestamp;
    private final Map<String, String> fieldErrors;

    public ErrorResponse(ExceptionResponseStatus ers) {
        this.code = ers.getCode();
        this.status = ers.getStatus();
        this.message = ers.getMessage();
        this.timestamp = LocalDateTime.now();
        fieldErrors = null;
    }
    public ErrorResponse(ExceptionResponseStatus ers, String message) {
        this.code = ers.getCode();
        this.status = ers.getStatus();
        this.message = message;
        this.timestamp = LocalDateTime.now();
        fieldErrors = null;
    }

    public ErrorResponse(ExceptionResponseStatus ers, Map<String, String> fieldErrors) {
        this.code = ers.getCode();
        this.status = ers.getStatus();
        this.message = ers.getMessage();
        this.timestamp = LocalDateTime.now();
        this.fieldErrors = fieldErrors;
    }
}
