package com.github.codingsoldier.example.cloudweb01.filter;

import static com.github.codingsoldier.common.constant.TraceConstant.X_REQ_TRACE_ID;

import com.github.codingsoldier.common.util.TraceUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 请求链路追踪过滤器。
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TraceIdFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String traceId = TraceUtil.putMdcTraceId(request.getHeader(X_REQ_TRACE_ID));
        response.setHeader(X_REQ_TRACE_ID, traceId);
        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(X_REQ_TRACE_ID);
        }
    }
}
