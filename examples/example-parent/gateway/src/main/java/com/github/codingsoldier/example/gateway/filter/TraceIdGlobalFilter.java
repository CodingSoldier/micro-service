package com.github.codingsoldier.example.gateway.filter;

import com.github.codingsoldier.common.constant.TraceConstant;
import com.github.codingsoldier.common.util.TraceUtil;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关链路追踪过滤器。
 */
@Component
public class TraceIdGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String traceId = TraceUtil.putMdcTraceId(exchange.getRequest().getHeaders()
                .getFirst(TraceConstant.X_REQ_TRACE_ID));
        exchange.getResponse().getHeaders().set(TraceConstant.X_REQ_TRACE_ID, traceId);
        ServerWebExchange tracedExchange = exchange.mutate()
                .request(builder -> builder.headers(headers -> headers.set(TraceConstant.X_REQ_TRACE_ID, traceId)))
                .build();
        return chain.filter(tracedExchange)
                .doFinally(signalType -> MDC.remove(TraceConstant.X_REQ_TRACE_ID));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
