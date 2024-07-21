package com.kuit.healthmate.global.exception;

import com.kuit.healthmate.global.response.ExceptionResponseStatus;
import lombok.Getter;

@Getter
public class HabitException extends RuntimeException{

    private final ExceptionResponseStatus exceptionStatus;

    public HabitException(ExceptionResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
    public HabitException(ExceptionResponseStatus exceptionStatus, String message) {
        super(message);
        this.exceptionStatus = exceptionStatus;
    }
}
