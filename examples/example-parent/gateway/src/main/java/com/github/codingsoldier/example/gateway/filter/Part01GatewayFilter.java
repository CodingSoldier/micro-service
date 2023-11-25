package com.github.codingsoldier.example.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 局部过滤器
 */
@Slf4j
public class Part01GatewayFilter implements GatewayFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取request
        ServerHttpRequest request = exchange.getRequest();
        log.info("进入局部过滤器，request={}", request);

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        // order代表的优先级是从小往大排序的，数值小的先执行
        // HIGHEST_PRECEDENCE = Integer.MIN_VALUE
        return HIGHEST_PRECEDENCE + 2;
    }

}
