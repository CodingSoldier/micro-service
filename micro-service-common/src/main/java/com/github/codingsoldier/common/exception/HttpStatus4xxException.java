package com.github.codingsoldier.common.exception;


import com.github.codingsoldier.common.enums.ResultCodeEnum;

/**
 * http status 4xx异常类
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class HttpStatus4xxException extends MicroServiceException {

    public HttpStatus4xxException(String message) {
        super(ResultCodeEnum.BAD_REQUEST.getCode(), message);
    }

    public HttpStatus4xxException(int code, String message) {
        super(code, message);
    }

    public HttpStatus4xxException(ResultCodeEnum responseCodeEnum) {
        super(responseCodeEnum);
    }

    public HttpStatus4xxException(ResultCodeEnum responseCodeEnum, String message) {
        super(responseCodeEnum, message);
    }

}