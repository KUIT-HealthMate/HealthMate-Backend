package com.kuit.healthmate.global.exception;

import com.kuit.healthmate.global.response.ExceptionResponseStatus;
import lombok.Getter;

@Getter
public class UserException extends RuntimeException {
    private final ExceptionResponseStatus exceptionStatus;

    public UserException (ExceptionResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}
