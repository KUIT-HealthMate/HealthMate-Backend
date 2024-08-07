package com.kuit.healthmate.global.exception_handler;

import com.kuit.healthmate.global.exception.HabitException;
import com.kuit.healthmate.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.kuit.healthmate.global.response.ExceptionResponseStatus.INVALID_HABIT_VALUE;

@Slf4j
@Order(1)
@RestControllerAdvice(basePackages = "com.kuit.healthmate.challenge.habit")
public class HabitExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HabitException.class)
    public ErrorResponse handle_HabitException(Exception e) {
        log.error("[handle_HabitException]", e);
        return new ErrorResponse(INVALID_HABIT_VALUE,e.getMessage()) ;
    }
}
