package com.github.codingsoldier.example.gateway.filter;

import com.github.codingsoldier.example.gateway.util.SleuthLogUtil;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * 打印响应日志
 */
@Slf4j
@Component
public class LogResponseFilter implements GlobalFilter, Ordered {

    private int printMaxByte = 10240;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String methodValue = request.getMethodValue();
        String requestURI = request.getURI().getPath();
        ServerHttpResponse response = exchange.getResponse();
        DataBufferFactory bufferFactory = response.bufferFactory();
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                MediaType mediaType = this.getDelegate().getHeaders().getContentType();
                boolean isPrintMedia = Objects.isNull(mediaType)
                        || MediaType.TEXT_PLAIN.isCompatibleWith(mediaType)
                        || MediaType.APPLICATION_JSON.isCompatibleWith(mediaType);
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        // 缓存body byte
                        int readableByteCount = dataBuffer.readableByteCount();
                        DataBuffer tempDataBuffer = dataBuffer.slice(0, readableByteCount);
                        byte[] tempByte = new byte[readableByteCount];
                        tempDataBuffer.read(tempByte);

                        // 日志
                        if (readableByteCount > printMaxByte) {
                            SleuthLogUtil.log(exchange, () ->
                                    log.info("response requestURI={} 返回值数据大于{}，不打印返回值。",
                                            requestURI, printMaxByte));
                        } else if (isPrintMedia) {
                            DataBuffer logDataBuffer = dataBuffer.slice(0, readableByteCount);
                            byte[] logByte = new byte[readableByteCount];
                            logDataBuffer.read(logByte);
                            String logData = new String(logByte, StandardCharsets.UTF_8);
                            printLog(exchange, requestURI, methodValue, logData);
                        } else {
                            SleuthLogUtil.log(exchange, () ->
                                    log.info("response requestURI={} mediaType={}，不打印返回值。",
                                            requestURI, mediaType));
                        }
                        return bufferFactory.wrap(tempByte);
                    }));
                }
                return super.writeWith(body);
            }
        };
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    @Override
    public int getOrder() {
        // -1 is response write filter, must be called before that
        return -2;
    }

    /**
     * 打印日志
     *
     * @param exchange
     * @param requestURI
     * @param responseBody
     */
    private void printLog(ServerWebExchange exchange, String requestURI, String method, String responseBody) {
        LinkedHashMap<String, Object> logData = new LinkedHashMap<>();
        logData.put("method", method);
        logData.put("requestURI", requestURI);
        logData.put("responseBody", responseBody);
        // 添加traceId，非常耗性能
        SleuthLogUtil.log(exchange, () -> log.info("网关打印response数据。{}", logData));
    }

}
