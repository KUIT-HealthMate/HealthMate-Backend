package com.kuit.healthmate.global.exception_handler;

import com.kuit.healthmate.global.exception.CustomJwtException;
import com.kuit.healthmate.global.response.ErrorResponse;
import com.kuit.healthmate.global.response.ExceptionResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(0)
@RestControllerAdvice(basePackages = "com/kuit/healthmate")
public class CustomJwtExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomJwtException.class)
    public ErrorResponse handle_CustomJwtException(Exception e) {
        log.error("[handle_CustomJwtException]", e);
        return new ErrorResponse(ExceptionResponseStatus.JWT_ERROR, e.getMessage()) ;
    }
}
