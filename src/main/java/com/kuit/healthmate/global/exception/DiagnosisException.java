package com.kuit.healthmate.global.exception;

import com.kuit.healthmate.global.response.ExceptionResponseStatus;
import lombok.Getter;

@Getter
public class DiagnosisException extends RuntimeException{

    private final ExceptionResponseStatus exceptionStatus;

    public DiagnosisException(ExceptionResponseStatus exceptionStatus, String message) {
        super(message);
        this.exceptionStatus = exceptionStatus;
    }
}
