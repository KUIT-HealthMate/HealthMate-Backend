package com.kuit.healthmate.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.annotation.Annotation;

@Getter
@RequiredArgsConstructor
public enum ExceptionResponseStatus {
    /**
     * 1000: 성공
     */
    SUCCESS(1000,HttpStatus.OK.value(), "요청에 성공하였습니다."),


    /**
     * 2000: Request 오류
     */
    BAD_REQUEST(2000, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 요청입니다."),
    URL_NOT_FOUND(2001, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 URL 입니다."),
    /**
     * 3000: Habit 오류
     */

    INVALID_HABIT_VALUE(3001, HttpStatus.BAD_REQUEST.value(), "요청에서 잘못된 값이 존재합니다."),
    NOT_EXIST_HABIT(3002, HttpStatus.BAD_REQUEST.value(),"존재하지 않는 습관입니다.");

    /**
     * 5000: User 관련 오류
     */
    INVALID_USER_ID(5000, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 유저 아이디 입니다."),

    /**
     * 6000: Supplement 관련 오류
     */
    INVALID_SUPPLEMENT_ID(5000, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 영양제 아이디 입니다.");

    private final int code;
    private final int status;
    private final String message;

}
