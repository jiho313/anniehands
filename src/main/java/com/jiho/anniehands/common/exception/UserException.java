package com.jiho.anniehands.common.exception;

public class UserException extends AnnieHandsException {
    public UserException(CustomErrorCode errorCode) {
        super(errorCode);
    }

    public UserException(String errorMessage) {
        super(errorMessage);
    }
}
