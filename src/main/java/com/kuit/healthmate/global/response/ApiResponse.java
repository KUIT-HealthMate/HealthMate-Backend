package com.kuit.healthmate.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.kuit.healthmate.global.response.ExceptionResponseStatus.SUCCESS;

@Getter
public class ApiResponse<T> {
    private final int code;
    private final int status;
    private final String message;
    private final LocalDateTime timestamp;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private final T result;

    public ApiResponse(T result) {
        this.code = SUCCESS.getCode();
        this.status = SUCCESS.getStatus();
        this.message = SUCCESS.getMessage();
        this.timestamp = LocalDateTime.now();
        this.result = result;
    }
}
