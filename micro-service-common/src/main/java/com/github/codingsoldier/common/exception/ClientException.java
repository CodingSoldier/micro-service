package com.github.codingsoldier.common.exception;


import com.github.codingsoldier.common.enums.ResultCodeEnum;

/**
 * 客户端异常
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class ClientException extends MicroServiceException {

    public ClientException(String message) {
        super(ResultCodeEnum.BAD_REQUEST.getCode(), message);
    }

    public ClientException(int code, String message) {
        super(code, message);
    }

    public ClientException(ResultCodeEnum responseCodeEnum) {
        super(responseCodeEnum);
    }

    public ClientException(ResultCodeEnum responseCodeEnum, String message) {
        super(responseCodeEnum, message);
    }

}