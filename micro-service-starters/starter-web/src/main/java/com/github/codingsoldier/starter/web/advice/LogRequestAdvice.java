package com.github.codingsoldier.starter.web.advice;

import com.github.codingsoldier.common.util.objectmapper.ObjectMapperUtil;
import com.github.codingsoldier.starter.web.properties.LoggingProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.TreeMap;

/**
 * Request日志
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Slf4j
@RestControllerAdvice
public class LogRequestAdvice extends CommonsRequestLoggingFilter implements RequestBodyAdvice {

    private LoggingProperties properties;

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
        try {
            if (properties.isRequestResponseLog() || properties.isRequestLog()) {
                String queryString = request.getQueryString();
                String decodeQueryString = StringUtils.isNotBlank(queryString)
                        ? URLDecoder.decode(queryString, StandardCharsets.UTF_8.name()) : "";
                log.info("打印请求，method = {}，url = {}，queryString解码：{}",
                        request.getMethod(), request.getRequestURL().toString(), decodeQueryString);
                if (properties.isIncludeHeaders()) {
                    TreeMap<String, String> map = new TreeMap<>();
                    Enumeration<String> headerNames = request.getHeaderNames();
                    while (headerNames.hasMoreElements()){
                        String headerName = headerNames.nextElement();
                        map.put(headerName, request.getHeader(headerName));
                    }
                    log.info("打印请求headers = {}", map.toString());
                }
            }
        } catch (Exception e) {
            log.error("打印request log 时，发生异常", e);
        }
    }

    /**
     * Writes a log message after the request is processed.
     */
    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        // 不处理
    }

    /**
     * RequestBodyAdvice 方法
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * The default implementation returns the InputMessage that was passed in.
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter,
                                           Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {

        return inputMessage;
    }

    /**
     * 打印请求body
     */
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

        log.info("打印请求body = {}", ObjectMapperUtil.writeValueAsString(body));
        return body;
    }

    /**
     * The default implementation returns the body that was passed in.
     */
    @Override
    @Nullable
    public Object handleEmptyBody(@Nullable Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                  Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

        return body;
    }

}
