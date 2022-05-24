package com.github.codingsoldier.common.exception;


import com.github.codingsoldier.common.enums.ResponseCodeEnum;

/**
 * feign调用，返回结果不是Result.code不是成功
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class ResultNotSuccessFeignException extends RuntimeException {

    private Integer code;
    private String message;

    public ResultNotSuccessFeignException(String message) {
        super(message);
        this.code = ResponseCodeEnum.BAD_REQUEST.getCode();
        this.message = message;
    }

    public ResultNotSuccessFeignException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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