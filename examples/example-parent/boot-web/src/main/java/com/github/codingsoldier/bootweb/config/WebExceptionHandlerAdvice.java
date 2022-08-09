package com.github.codingsoldier.bootweb.config;


import com.github.codingsoldier.common.exception.AppException;
import com.github.codingsoldier.common.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 使用 @Order 排序值，覆盖 micro-service 默认异常处理
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebExceptionHandlerAdvice {

    /**
     * AppException
     *
     * @param ex ex
     * @return result
     */
    @ExceptionHandler(AppException.class)
    public Result<Object> appExceptionHandler(final AppException ex) {
        log.error("web全局异常捕获AppException", ex);
        return Result.fail(ex.getCode(), ex.getMessage());
    }


}
