package com.jiho.anniehands.global.exception;

import lombok.Getter;

@Getter
public class UserException extends AnnieHandsException {

    private String redirectUrl;

    public UserException(CustomErrorCode errorCode, String redirectUrl) {
        super(errorCode);
        this.redirectUrl = redirectUrl;
    }
    public UserException(CustomErrorCode errorCode) {
        super(errorCode);
    }

    public UserException(String errorMessage) {
        super(errorMessage);
    }
}
