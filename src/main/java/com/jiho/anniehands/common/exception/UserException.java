package com.jiho.anniehands.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
