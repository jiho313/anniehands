package com.jiho.anniehands.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode {
    NO_MATCHING_MEMBER("400", "없는 회원입니다."),
    INVALID_VERIFICATION_CODE("400", "입력하신 인증번호가 일치하지 않습니다."),
    MISMATCHED_PASSWORD("400", "비밀번호가 일치하지 않습니다."),
    DUPLICATE_EMAIL("409", "이미 사용 중인 이메일입니다."),
    DUPLICATE_USERNAME("409", "이미 사용 중인 아이디입니다."),
    NOT_FOUND("404", "페이지를 찾을 수 없습니다.");

    private final String code;
    private final String message;

}

