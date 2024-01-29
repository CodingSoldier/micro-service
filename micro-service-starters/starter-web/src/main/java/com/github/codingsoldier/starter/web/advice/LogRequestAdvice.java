package com.github.codingsoldier.starter.web.advice;

import com.github.codingsoldier.common.constant.OrderConstant;
import com.github.codingsoldier.common.util.objectmapper.ObjectMapperUtil;
import com.github.codingsoldier.starter.web.properties.LoggingProperties;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.PathContainer;
import org.springframework.lang.Nullable;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import org.springframework.web.util.pattern.PathPatternParser;

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
        boolean isExclude = isExclude(request.getRequestURI(), properties.getExcludeURIPath());
        if (isExclude) {
            return false;
        }
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
            log.info("在请求完成后打印，requestUri = {}，requestBody为空，queryString解码 = {}，method = {}", this.requestUri, decodeQueryString, this.methodName);
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

    /**
     * 是否排除uri
     * @param requestURI requestURI
     * @param excludeURIPath excludeURIPath
     * @return 是否排除uri
     */
    public static boolean isExclude(String requestURI, List<String> excludeURIPath) {
        PathPatternParser pathPatternParser = new PathPatternParser();
        boolean isExclude = false;
        if (CollectionUtils.isNotEmpty(excludeURIPath)) {
            for (String exclude : excludeURIPath) {
                isExclude = pathPatternParser.parse(exclude)
                    .matches(PathContainer.parsePath(requestURI));
                if (isExclude) {
                    break;
                }
            }
        }
        return isExclude;
    }

}
