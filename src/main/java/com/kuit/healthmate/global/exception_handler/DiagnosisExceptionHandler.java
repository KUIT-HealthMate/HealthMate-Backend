package com.kuit.healthmate.global.exception_handler;

import com.kuit.healthmate.global.exception.DiagnosisException;
import com.kuit.healthmate.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.kuit.healthmate.global.response.ExceptionResponseStatus.INVALID_DIAGNOSIS_VALUE;

@Slf4j
@Order(1)
@RestControllerAdvice(basePackages = "com.kuit.healthmate.diagnosis")
public class DiagnosisExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DiagnosisException.class)
    public ErrorResponse handle_DiagnosisException(Exception e) {
        log.error("[handle_DiagnosisException]", e);
        return new ErrorResponse(INVALID_DIAGNOSIS_VALUE,e.getMessage()) ;
    }
}
