package com.github.codingsoldier.common.exception;


import com.github.codingsoldier.common.enums.ResponseCodeEnum;

/**
 * 自定义异常
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class AppException extends RuntimeException {

    private final Integer code;
    private final String message;

    public AppException(String message) {
        super(message);
        this.code = ResponseCodeEnum.BAD_REQUEST.getCode();
        this.message = message;
    }

    public AppException(Integer code, String message) {
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

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}