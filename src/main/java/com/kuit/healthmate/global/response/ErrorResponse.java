package com.kuit.healthmate.global.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse<T> {
    private final int code;
    private final int status;
    private final String message;
    private final LocalDateTime timestamp;

    public ErrorResponse(int code, int status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
