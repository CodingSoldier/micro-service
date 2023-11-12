package com.github.codingsoldier.common.exception.feign;

import com.github.codingsoldier.common.enums.ResultCodeEnum;

/**
 * feign调用，下游服务的Result#code不是成功code
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class FeignResultErrorException extends RuntimeException {

    private final Integer code;
    private final String message;

    public FeignResultErrorException(String message) {
        super(message);
        this.code = ResultCodeEnum.FEIGN_RESULT_ERROR_EX.getCode();
        this.message = message;
    }

    public FeignResultErrorException(Integer code, String message) {
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