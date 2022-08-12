package com.github.codingsoldier.starter.openfeign.advice;


import com.github.codingsoldier.common.enums.ResponseCodeEnum;
import com.github.codingsoldier.common.resp.Result;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
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
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class FeignExceptionHandlerAdvice {

    /**
     *
     * feign客户端未添加 fallback 降级策略 且 服务提供方上线后掉线，短时间内调用，抛出 RetryableException，
     * 被此捕获器捕获
     */
    @ExceptionHandler(value = FeignException.class)
    public Result<Object> feignExceptionHandler(final FeignException ex) {
        log.error("捕获feign异常", ex);
        return Result.fail(ResponseCodeEnum.BAD_REQUEST.getCode(), "服务之间调用异常。");
    }
}
