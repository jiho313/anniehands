package com.jiho.anniehands.exception;

import lombok.Getter;

@Getter
public enum CustomErrorCode {
    // Enum 에러 코드 정의
    MISMATCHED_PASSWORD("400","비밀번호가 일치하지 않습니다."),
    DUPLICATE_EMAIL("409", "이미 사용 중인 이메일입니다."),
    DUPLICATE_USERNAME("409", "이미 사용 중인 아이디입니다.");

    private final String code;
    private final String message;

     CustomErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}

