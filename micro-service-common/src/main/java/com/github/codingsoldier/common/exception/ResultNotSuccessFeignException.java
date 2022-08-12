package com.github.codingsoldier.common.exception;


import com.github.codingsoldier.common.enums.ResponseCodeEnum;

/**
 * feign调用，返回结果不是Result.code不是成功
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class ResultNotSuccessFeignException extends RuntimeException {

    private final Integer code;
    private final String message;

    public ResultNotSuccessFeignException(String message) {
        super(message);
        this.code = ResponseCodeEnum.RESULT_NOT_SUCCESS_FEIGN_EX.getCode();
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

    @Override
    public String getMessage() {
        return message;
    }

}