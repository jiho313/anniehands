package com.jiho.anniehands.user;

import com.jiho.anniehands.exception.AnnieHandsException;
import com.jiho.anniehands.exception.CustomErrorCode;

public class UserException extends AnnieHandsException {
    public UserException(CustomErrorCode errorCode) {
        super(errorCode);
    }

    public UserException(String errorMessage) {
        super(errorMessage);
    }
}
