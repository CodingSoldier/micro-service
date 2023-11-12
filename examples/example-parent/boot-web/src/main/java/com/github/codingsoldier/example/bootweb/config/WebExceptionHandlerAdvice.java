package com.github.codingsoldier.example.bootweb.config;


import com.github.codingsoldier.common.exception.ClientException;
import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.common.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * 使用 @Order 排序值，覆盖 micro-service 默认异常处理
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebExceptionHandlerAdvice {

    @ExceptionHandler(ClientException.class)
    public Result<Object> clientException(final ClientException ex, HttpServletResponse response) {
        log.error("#############捕获ClientException", ex);
        response.setStatus(CommonUtil.getResponseStatus(ex.getCode()));
        return Result.fail(ex.getCode(), ex.getMessage());
    }


}
