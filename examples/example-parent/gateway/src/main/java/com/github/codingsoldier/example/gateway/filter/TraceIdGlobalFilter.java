package com.github.codingsoldier.example.gateway.filter;

import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class TraceIdGlobalFilter implements GlobalFilter, Ordered {

    private static final String X_REQ_TRACE_ID = "x-req-trace-id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String traceId = exchange.getRequest().getHeaders().getFirst(X_REQ_TRACE_ID);
        if (traceId != null && !traceId.isEmpty()) {
            MDC.put(X_REQ_TRACE_ID, traceId);
        }
        return chain.filter(exchange)
                .doFinally(signalType -> MDC.remove(X_REQ_TRACE_ID));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
