package com.kuit.healthmate.global.exception_handler;

import com.kuit.healthmate.global.exception.SupplementException;
import com.kuit.healthmate.global.response.ErrorResponse;
import com.kuit.healthmate.global.response.ExceptionResponseStatus;
import jakarta.annotation.Priority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Priority(0)
@RestControllerAdvice
public class SupplementExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SupplementException.class)
    public ErrorResponse handle_HabitException(Exception e) {
        log.error("[handle_SupplementException]", e);
        return new ErrorResponse(ExceptionResponseStatus.INVALID_SUPPLEMENT_ID) ;
    }
}
