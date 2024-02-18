package com.jiho.anniehands.common.exception;

import lombok.Getter;

@Getter
public class PageException extends AnnieHandsException {

    private String redirectUrl;

    public PageException(CustomErrorCode errorCode, String redirectUrl) {
        super(errorCode);
        this.redirectUrl = redirectUrl;
    }

    public PageException(CustomErrorCode errorCode) {
        super(errorCode);
    }

    public PageException(String errorMessage) {
        super(errorMessage);
    }
}
