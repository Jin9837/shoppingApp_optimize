package com.example.shoppingapp_3.exception;

public enum CustomizedExceptionEnum {
    NAME_EXISTED(10001, "USERNAME EXISTED"),
    EMAIL_EXISTED(10002, "EMAIL EXISTED"),
    REGISTRATION_FAILED(10003, "REGISTRATION FAILED"),
    ACCESS_DENIED(10004, "INSUFFICIENT PERMISSIONS"),
    AUTHORIZED_FAILED(10005, "INCORRECT CREDENTIALS, PLEASE TRY AGAIN."),
    NEED_USER_NAME(10006, "EMPTY USERNAME"),
    NEED_PASSWORD(10007, "EMPTY PASSWORD"),
    WRONG_PASSWORD(10008, "WRONG PASSWORD"),
    NOT_ENOUGH_INVENTORY(10009, "NOT ENOUGH INVENTORY"),
    ACCESS_IS_DENIED(10010, "ACCESS IS DENIED"),
    SYSTEM_ERROR(20000, "System exception, please check specific error information from the console or log.");

    Integer code;
    String msg;

    CustomizedExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
