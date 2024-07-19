package com.kuit.healthmate.global.exception_handler;

import com.kuit.healthmate.global.exception.BadRequestException;
import com.kuit.healthmate.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import static com.kuit.healthmate.global.response.ExceptionResponseStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class, NoHandlerFoundException.class, TypeMismatchException.class})
    public ErrorResponse handle_BadRequest(Exception e) {
        log.error("[handle_BadRequest]", e);
        return new ErrorResponse(URL_NOT_FOUND) {
        };
    }
}
