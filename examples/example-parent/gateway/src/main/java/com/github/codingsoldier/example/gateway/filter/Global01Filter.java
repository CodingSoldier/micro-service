package com.github.codingsoldier.example.gateway.filter;

import com.github.codingsoldier.common.constant.TraceConstant;
import com.github.codingsoldier.common.util.TraceUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class Global01Filter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        TraceUtil.putMdcTraceId(exchange.getRequest().getHeaders().getFirst(TraceConstant.X_REQ_TRACE_ID));
        try {
            log.info("全局过滤器");
        } finally {
            MDC.remove(TraceConstant.X_REQ_TRACE_ID);
        }
        return chain.filter(exchange);
    }
}
