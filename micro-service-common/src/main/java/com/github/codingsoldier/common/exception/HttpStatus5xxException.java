package com.github.codingsoldier.common.exception;


import com.github.codingsoldier.common.enums.ResultCodeEnum;

/**
 * http status 5xx异常类
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class HttpStatus5xxException extends MicroServiceException {

    public HttpStatus5xxException(String message) {
        super(ResultCodeEnum.SERVER_ERROR.getCode(), message);
    }

    public HttpStatus5xxException(int code, String message) {
        super(code, message);
    }

    public HttpStatus5xxException(ResultCodeEnum responseCodeEnum) {
        super(responseCodeEnum);
    }

    public HttpStatus5xxException(ResultCodeEnum responseCodeEnum, String message) {
        super(responseCodeEnum, message);
    }

}