package com.github.codingsoldier.starter.openfeign.advice;


import com.github.codingsoldier.common.enums.ResponseCodeEnum;
import com.github.codingsoldier.common.resp.Result;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * feign异常处理，@Order需要比 com.github.codingsoldier.starterweb.advice.ExceptionHandlerAdvice 小
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Slf4j
@RestControllerAdvice
public class FeignExceptionHandlerAdvice {

    @ExceptionHandler(value = FeignException.class)
    public Result feignExceptionHandler(final FeignException ex) {
        log.error("捕获feign异常", ex);
        return Result.fail(ResponseCodeEnum.BAD_REQUEST.getCode(), "服务调用异常。");
    }
}
