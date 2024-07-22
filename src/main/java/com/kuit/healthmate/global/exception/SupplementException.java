package com.kuit.healthmate.global.exception;

import com.kuit.healthmate.global.response.ExceptionResponseStatus;

public class SupplementException extends RuntimeException{

    private final ExceptionResponseStatus exceptionStatus;

    public SupplementException (ExceptionResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}
