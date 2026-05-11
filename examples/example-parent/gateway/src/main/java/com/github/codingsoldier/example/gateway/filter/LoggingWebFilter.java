package com.github.codingsoldier.example.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 打印请求日志
 */
@Slf4j
@Component
public class LoggingWebFilter extends OncePerRequestFilter {

    @Value("${custom.logging.requestResponseLog:true}")
    private boolean requestResponseLog;
    @Value("${custom.logging.requestLog:true}")
    private boolean requestLog;
    @Value("${custom.logging.responseBodyLog:true}")
    private boolean responseBodyLog;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper =
            new ContentCachingRequestWrapper(request, 1024 * 1024);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            logRequest(requestWrapper);
            logResponse(requestWrapper, responseWrapper);
            responseWrapper.copyBodyToResponse();
        }
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        if (!requestResponseLog && !requestLog) {
            return;
        }
        byte[] content = request.getContentAsByteArray();
        String requestBody = content.length == 0 ? null : new String(content, StandardCharsets.UTF_8);
        Map<String, String[]> queryParams = request.getParameterMap();
        if (requestBody == null || requestBody.isBlank()) {
            log.info("请求信息，uriPath={}, methodName={} , queryParams={}",
                request.getRequestURI(), request.getMethod(), queryParams);
            return;
        }
        log.info("请求信息，uriPath={}, methodName={} , queryParams={}, requestBody={}",
            request.getRequestURI(), request.getMethod(), queryParams, requestBody);
    }

    private void logResponse(ContentCachingRequestWrapper request,
        ContentCachingResponseWrapper response) throws IOException {
        if (!requestResponseLog && !responseBodyLog) {
            return;
        }
        MediaType contentType = null;
        if (response.getContentType() != null) {
            contentType = MediaType.parseMediaType(response.getContentType());
        }
        boolean isExcel = contentType != null && contentType.toString().contains("excel");
        if (isExcel) {
            return;
        }
        String responseBody = StreamUtils.copyToString(response.getContentInputStream(), StandardCharsets.UTF_8);
        log.info("响应信息，uriPath={}, methodName={}, responseBody={}",
            request.getRequestURI(), request.getMethod(), responseBody);
    }
}
