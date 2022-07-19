package com.github.codingsoldier.example.gateway.filter;

import com.github.codingsoldier.example.gateway.util.SleuthLogUtil;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.sleuth.CurrentTraceContext;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.instrument.web.WebFluxSleuthOperators;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * 打印响应日志
 */
@Slf4j
@Component
public class LogResponseFilter implements GlobalFilter, Ordered {

    private int printMaxByte = 102400000;

    @Autowired
    Tracer tracer;

    @Autowired
    CurrentTraceContext currentTraceContext;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String methodValue = request.getMethodValue();
        String requestURI = request.getURI().getPath();
        ServerHttpResponse response = exchange.getResponse();

        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                HttpHeaders delegateRespHeaders = this.getDelegate().getHeaders();
                MediaType mediaType = delegateRespHeaders.getContentType();
                boolean isPrintMedia = Objects.isNull(mediaType)
                        || MediaType.TEXT_PLAIN.isCompatibleWith(mediaType)
                        || MediaType.APPLICATION_JSON.isCompatibleWith(mediaType);
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                    // https://docs.spring.io/spring-cloud-sleuth/docs/current/reference/html/integrations.html#sleuth-reactor-integration
                    // WebFluxSleuthOperators.withSpanInScope(tracer, currentTraceContext, exchange, () -> log.error("TESTABCTEST"));
                    return super.writeWith(fluxBody
                            .map(dataBuffer -> dataBuffer)
                            .doOnEach(WebFluxSleuthOperators
                        .withSpanInScope(SignalType.ON_NEXT, signal -> {
                            Object obj = signal.get();
                            if (obj instanceof DataBuffer) {
                                DataBuffer dataBuffer = (DataBuffer) obj;
                                int readableByteCount = dataBuffer.readableByteCount();
                                DataBuffer logDataBuffer = dataBuffer.slice(0, readableByteCount);
                                byte[] logByte = new byte[readableByteCount];
                                logDataBuffer.read(logByte);
                                String bodyData = new String(logByte, StandardCharsets.UTF_8);
                                // 日志
                                if (readableByteCount > printMaxByte) {
                                    log.info("response requestURI={} 返回值数据大于{}，不打印返回值。",
                                            requestURI, printMaxByte);
                                } else if (isPrintMedia) {
                                    LinkedHashMap<String, Object> logAll = new LinkedHashMap<>();
                                    logAll.put("method", methodValue);
                                    logAll.put("requestURI", requestURI);
                                    logAll.put("responseBody", bodyData);
                                    log.info("网关打印response数据。{}", logAll);
                                } else {
                                    log.info("response requestURI={} mediaType={}，不打印返回值。",
                                            requestURI, mediaType);
                                }
                            }
                        })));
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
