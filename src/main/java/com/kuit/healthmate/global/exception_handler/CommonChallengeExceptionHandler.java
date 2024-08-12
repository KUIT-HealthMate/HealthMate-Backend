package com.kuit.healthmate.global.exception_handler;

import com.kuit.healthmate.global.exception.BadRequestException;
import com.kuit.healthmate.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TypeMismatchException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import static com.kuit.healthmate.global.response.ExceptionResponseStatus.*;

@Slf4j
@Order(1)
@RestControllerAdvice(basePackages = "com.kuit.healthmate.challenge.common.controller")
public class CommonChallengeExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class, NoHandlerFoundException.class, TypeMismatchException.class})
    public ErrorResponse handleBadRequest(Exception e) {
        log.error("[handleBadRequest]", e);
        return new ErrorResponse(URL_NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("[handleValidationExceptions]", ex);

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ErrorResponse(INVALID_REQUEST_BODY, errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DateTimeParseException.class)
    public ErrorResponse handleDateTimeParseException(DateTimeParseException e) {
        log.error("[handleDateTimeParseException]", e);
        return new ErrorResponse(INVALID_DATE_FORMAT);
    }
}
