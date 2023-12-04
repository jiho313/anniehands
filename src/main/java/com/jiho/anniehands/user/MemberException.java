package com.jiho.anniehands.user;

import com.jiho.anniehands.exception.AnnieHandsException;
import com.jiho.anniehands.exception.CustomErrorCode;

public class MemberException extends AnnieHandsException {
    public MemberException(CustomErrorCode errorCode) {
        super(errorCode);
    }

    public MemberException(String errorMessage) {
        super(errorMessage);
    }
}
