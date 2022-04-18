package com.github.codingsoldier.common.exception;


import com.github.codingsoldier.common.enums.RespCodeEnum;

public class AppException extends RuntimeException {

    private int code;
    private String message;

    public AppException(String message) {
        super(message);
        this.code = RespCodeEnum.FAIl.getCode();
        this.message = message;
    }

    public AppException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public AppException(RespCodeEnum respCodeEnum) {
        super(respCodeEnum.getMessage());
        this.code = respCodeEnum.getCode();
        this.message = respCodeEnum.getMessage();
    }

    public AppException(RespCodeEnum respCodeEnum, String message) {
        super(message);
        this.code = respCodeEnum.getCode();
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}