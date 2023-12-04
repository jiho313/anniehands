package com.jiho.anniehands.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ErrorResponse {
    // 필드 정의
    private String errorCode;
    private String errorMessage;
    private List<FieldError> fieldErrors;

    // Setter 연산자 대신 의미 있는 메서드
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static class FieldError {
        private String field;
        private String message;

        // 필요한 생성자를 추가
        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static ErrorResponse of(CustomErrorCode errorCode) {
        return of(errorCode, null);
    }

    public static ErrorResponse of(CustomErrorCode errorCode, BindingResult bindingResult) {
        ErrorResponse response = new ErrorResponse();
        response.setErrorCode(errorCode.getCode());
        response.setErrorMessage(errorCode.getMessage());
        if (bindingResult != null) {
            response.fieldErrors = bindingResult.getFieldErrors().stream()
                    .map(fieldError -> new FieldError(fieldError.getField(), fieldError.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
        return response;
    }

}
