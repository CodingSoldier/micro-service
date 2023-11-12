package com.github.codingsoldier.common.exception;


import com.github.codingsoldier.common.enums.ResultCodeEnum;

/**
 * 基础异常类，建议不要直接使用此类，建议使用ClientException或者BackendServicesException
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class MicroServiceException extends RuntimeException {

    private final int code;
    private final String message;

    public MicroServiceException(String message) {
        super(message);
        this.code = ResultCodeEnum.BAD_REQUEST.getCode();
        this.message = message;
    }

    public MicroServiceException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public MicroServiceException(ResultCodeEnum responseCodeEnum) {
        super(responseCodeEnum.getMessage());
        this.code = responseCodeEnum.getCode();
        this.message = responseCodeEnum.getMessage();
    }

    public MicroServiceException(ResultCodeEnum responseCodeEnum, String message) {
        super(message);
        this.code = responseCodeEnum.getCode();
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}