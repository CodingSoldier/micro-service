package com.github.codingsoldier.example.bootweb.config;


import com.github.codingsoldier.common.exception.HttpStatus4xxException;
import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.common.util.CommonUtil;
import jakarta.servlet.http.HttpServletResponse;
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

    @ExceptionHandler(HttpStatus4xxException.class)
    public Result<Object> httpStatus4xxException(final HttpStatus4xxException ex, HttpServletResponse response) {
        log.error("#############捕获HttpStatus4xxException", ex);
        response.setStatus(CommonUtil.getResponseStatus(ex.getCode()));
        return Result.fail(ex.getCode(), ex.getMessage());
    }


}
