package com.github.codingsoldier.common.exception;


import com.github.codingsoldier.common.enums.ResultCodeEnum;

/**
 * 后端服务异常
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class BackendServicesException extends MicroServiceException {

    public BackendServicesException(String message) {
        super(ResultCodeEnum.SERVER_ERROR.getCode(), message);
    }

    public BackendServicesException(int code, String message) {
        super(code, message);
    }

    public BackendServicesException(ResultCodeEnum responseCodeEnum) {
        super(responseCodeEnum);
    }

    public BackendServicesException(ResultCodeEnum responseCodeEnum, String message) {
        super(responseCodeEnum, message);
    }

}