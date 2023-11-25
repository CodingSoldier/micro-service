package com.github.codingsoldier.starter.openfeign.advice;


import com.github.codingsoldier.common.enums.ResultCodeEnum;
import com.github.codingsoldier.common.resp.Result;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * feign异常处理，@Order需要比 com.github.codingsoldier.starter.web.advice.ExceptionHandlerAdvice 小
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class FeignExceptionHandlerAdvice {

    /**
     * feign客户端未添加 fallback 降级策略 且 服务提供方上线后掉线，短时间内调用，抛出 RetryableException，
     * 被此捕获器捕获
     */
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = FeignException.class)
    public Result<Object> feignExceptionHandler(final FeignException ex) {
        log.error("捕获feign异常", ex);
        return Result.fail(ResultCodeEnum.SERVER_ERROR.getCode(), "服务之间调用异常。");
    }
}
