package com.github.codingsoldier.example.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.ByteArrayOutputStream;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;

/**
 * 打印请求日志
 */
@Slf4j
@Component
public class LoggingWebFilter implements WebFilter {

    @Value("${custom.logging.requestResponseLog:true}")
    private boolean requestResponseLog;
    @Value("${custom.logging.requestLog:true}")
    private boolean requestLog;
    @Value("${custom.logging.responseBodyLog:true}")
    private boolean responseBodyLog;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();
        DataBufferFactory dataBufferFactory = response.bufferFactory();
        if (requestResponseLog || requestLog) {
            // 打印请求
            request = getDecoratedRequest(request);
        }
        if (requestResponseLog || responseBodyLog) {
            // 打印响应
            response = getDecoratedResponse(response, request, dataBufferFactory);
        }
        return chain.filter(exchange.mutate().request(request).response(response).build());
    }

    private ServerHttpResponseDecorator getDecoratedResponse(ServerHttpResponse response,
        ServerHttpRequest request, DataBufferFactory dataBufferFactory) {
        final String uriPath = request.getURI().getPath();
        final String methodName = request.getMethod().name();
        return new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(final Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux<? extends DataBuffer> fluxBody) {
                    return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                        DefaultDataBuffer joinedBuffers = new DefaultDataBufferFactory().join(dataBuffers);
                        byte[] content = new byte[joinedBuffers.readableByteCount()];
                        joinedBuffers.read(content);
                        String responseBody = new String(content, StandardCharsets.UTF_8);
                        log.info("响应信息，uriPath={}, methodName={}, responseBody={}", uriPath, methodName, responseBody);
                        return dataBufferFactory.wrap(responseBody.getBytes());
                    })).onErrorResume(err -> {
                        String msg = "获取responseBody异常，uriPath=" + uriPath;
                        log.error(msg, err);
                        return Mono.empty();
                    });
                }
                return super.writeWith(body);
            }
        };
    }

    private ServerHttpRequest getDecoratedRequest(ServerHttpRequest request) {
        final String uriPath = request.getURI().getPath();
        final String methodName = request.getMethod().name();
        final MultiValueMap<String, String> queryParams = request.getQueryParams();
        return new ServerHttpRequestDecorator(request) {
            @Override
            public Flux<DataBuffer> getBody() {
                return super.getBody().publishOn(Schedulers.boundedElastic()).doOnNext(dataBuffer -> {
                    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                        Channels.newChannel(baos).write(dataBuffer.toByteBuffer().asReadOnlyBuffer());
                        String requestBody = baos.toString(StandardCharsets.UTF_8);
                        log.info("请求信息，uriPath={}, methodName={} , queryParams={}, requestBody={}", uriPath, methodName, queryParams, requestBody);
                    } catch (Exception e) {
                        String msg = "获取requestBody异常，uriPath=" + uriPath;
                        log.error(msg, e);
                    }
                }).switchIfEmpty(Flux.defer(() -> {
                    log.info("请求信息，uriPath={}, methodName={} , queryParams={}", uriPath, methodName, queryParams);
                    return Flux.just();
                }));
            }
        };
    }
}
