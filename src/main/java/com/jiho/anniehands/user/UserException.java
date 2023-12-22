package com.jiho.anniehands.user;

import com.jiho.anniehands.common.exception.AnnieHandsException;
import com.jiho.anniehands.common.exception.CustomErrorCode;

public class UserException extends AnnieHandsException {
    public UserException(CustomErrorCode errorCode) {
        super(errorCode);
    }

    public UserException(String errorMessage) {
        super(errorMessage);
    }
}
