package com.kuit.healthmate.global.exception_handler;

import com.kuit.healthmate.global.response.ErrorResponse;
import com.kuit.healthmate.global.response.ExceptionResponseStatus;
import jdk.jshell.spi.ExecutionControl.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(0)
@RestControllerAdvice(basePackages = {"com.kuit.healthmate.user", "com.kuit.healthmate.challenge"})
public class UserExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException.class)
    public ErrorResponse handle_HabitException(Exception e) {
        log.error("[handle_UserException]", e);
        return new ErrorResponse(ExceptionResponseStatus.INVALID_USER_ID) ;
    }
}
