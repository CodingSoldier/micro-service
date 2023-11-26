package com.github.codingsoldier.starter.web.advice;

import com.github.codingsoldier.common.constant.OrderConstant;
import com.github.codingsoldier.common.util.objectmapper.ObjectMapperUtil;
import com.github.codingsoldier.starter.web.properties.LoggingProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Request日志
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Slf4j
@Order(OrderConstant.ADVICE_LOG_REQUEST)
@RestControllerAdvice
public class LogRequestAdvice extends CommonsRequestLoggingFilter implements RequestBodyAdvice {

    private String methodName;
    private String requestUri;
    private String decodeQueryString;
    private boolean logPrinted = false;

    private final LoggingProperties properties;

    public LogRequestAdvice(LoggingProperties properties) {
        this.properties = properties;
    }

    /**
     * CommonsRequestLoggingFilter 方法
     */
    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return properties.isRequestResponseLog() || properties.isRequestLog();
    }

    /**
     * 打印请求日志
     */
    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        this.methodName = request.getMethod();
        this.requestUri = request.getRequestURI();
        try {
            this.decodeQueryString = StringUtils.isNotBlank(this.decodeQueryString)
                    ? URLDecoder.decode(this.decodeQueryString, StandardCharsets.UTF_8) : "";
        } catch (Exception e) {
            log.error("queryString编码异常", e);
        }
        this.logPrinted = false;
    }

    /**
     * Writes a log message after the request is processed.
     */
    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        if (!this.logPrinted) {
            log.info("没有requestBody，在请求完成后打印，requestUri = {}，queryString解码 = {}，method = {}", this.requestUri, decodeQueryString, this.methodName);
            this.logPrinted = true;
        }
    }

    @Override
    public boolean supports(MethodParameter methodParameter,
            Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * The default implementation returns the InputMessage that was passed in.
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage,
            MethodParameter parameter,
            Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        return inputMessage;
    }

    /**
     * 打印requestBody
     */
    @Override
    public Object afterBodyRead(Object body,
            HttpInputMessage inputMessage,
            MethodParameter parameter,
            Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        String requestBody = ObjectMapperUtil.writeValueAsString(body);
        log.info("打印请求，requestUri = {}，requestBody = {}，queryString解码={}，method = {}", this.requestUri, requestBody, decodeQueryString, this.methodName);
        this.logPrinted = true;
        return body;
    }

    /**
     * The default implementation returns the body that was passed in.
     */
    @Override
    @Nullable
    public Object handleEmptyBody(@Nullable Object body,
            HttpInputMessage inputMessage,
            MethodParameter parameter,
            Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

}
