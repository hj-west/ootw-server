package com.responseor.ootw.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    UNREGISTERED_MEMBER(HttpStatus.BAD_REQUEST, "가입 되지 않은 회원 입니다.")
    , INCORRECT_MEMBER_INFORMATION(HttpStatus.BAD_REQUEST, "잘못된 회원 정보 입니다.")
    , EMAIL_EXISTS(HttpStatus.BAD_REQUEST, "이미 존재 하는 이메일 입니다.")
    , WEATHER_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Weather Api Error")

    ;

    private final HttpStatus httpStatus;
    private final String message;
}