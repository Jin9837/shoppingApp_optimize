package com.example.shoppingapp_3.exception;

public class CustomizedException extends Exception {

    private final Integer code;
    private final String message;

    public CustomizedException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public CustomizedException(CustomizedExceptionEnum exceptionEnum) {
        this(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
