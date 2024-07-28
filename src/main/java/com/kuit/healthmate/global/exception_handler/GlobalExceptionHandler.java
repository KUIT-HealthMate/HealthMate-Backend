package com.kuit.healthmate.global.exception_handler;

import com.kuit.healthmate.global.exception.BadRequestException;
import com.kuit.healthmate.global.exception.HabitException;
import com.kuit.healthmate.global.response.ErrorResponse;
import com.kuit.healthmate.global.response.ExceptionResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("[handle_BadRequest]", ex);

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ErrorResponse(INVALID_REQUEST_BODY, errors);
    }

}
