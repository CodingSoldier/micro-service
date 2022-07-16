package com.github.codingsoldier.example.gateway.filter;

import com.github.codingsoldier.example.gateway.log.SleuthLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class Global01Filter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        SleuthLog.log(exchange, () -> log.info("全局过滤器，request={}", request.toString()));
        return chain.filter(exchange);

        //boolean isAllow = true;
        //if (isAllow) {
        //    return chain.filter(exchange);
        //
        //} else {
        //    //设置status和body
        //    return Mono.defer(() -> {
        //        //setResponseStatus(exchange, HttpStatus.UNAUTHORIZED);
        //        final ServerHttpResponse response = exchange.getResponse();
        //        byte[] bytes = "Hello World".getBytes(StandardCharsets.UTF_8);
        //        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        //        response.getHeaders().set("aaa", "bbb");
        //        return response.writeWith(Flux.just(buffer));
        //    });
        //}

    }
}