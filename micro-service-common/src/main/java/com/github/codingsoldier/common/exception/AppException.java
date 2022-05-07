package com.github.codingsoldier.common.exception;


import com.github.codingsoldier.common.enums.ResponseCodeEnum;

/**
 * 自定义异常
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class AppException extends RuntimeException {

    private int code;
    private String message;

    public AppException(String message) {
        super(message);
        this.code = ResponseCodeEnum.BAD_REQUEST.getCode();
        this.message = message;
    }

    public AppException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public AppException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum.getMessage());
        this.code = responseCodeEnum.getCode();
        this.message = responseCodeEnum.getMessage();
    }

    public AppException(ResponseCodeEnum responseCodeEnum, String message) {
        super(message);
        this.code = responseCodeEnum.getCode();
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