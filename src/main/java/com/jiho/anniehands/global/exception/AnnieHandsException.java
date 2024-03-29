package com.jiho.anniehands.global.exception;

public class AnnieHandsException extends RuntimeException {
    public AnnieHandsException(CustomErrorCode errorCode) {
        super(errorCode.getMessage());
    }

    public AnnieHandsException(String errorMessage) {
        super(errorMessage);
    }

}
