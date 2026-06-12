package com.github.codingsoldier.example.gateway.filter;

import com.github.codingsoldier.common.constant.TraceConstant;
import com.github.codingsoldier.common.util.TraceUtil;
import java.io.ByteArrayOutputStream;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
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
import org.slf4j.MDC;

/**
 * 打印请求日志
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
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
        ServerHttpRequest request = withTraceId(exchange.getRequest());
        response.getHeaders().set(TraceConstant.X_REQ_TRACE_ID,
            request.getHeaders().getFirst(TraceConstant.X_REQ_TRACE_ID));
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

    private ServerHttpRequest withTraceId(ServerHttpRequest request) {
        String traceId = TraceUtil.getOrCreateTraceId(request.getHeaders()
            .getFirst(TraceConstant.X_REQ_TRACE_ID));
        return request.mutate()
            .headers(headers -> headers.set(TraceConstant.X_REQ_TRACE_ID, traceId))
            .build();
    }

    private ServerHttpResponseDecorator getDecoratedResponse(ServerHttpResponse response,
        ServerHttpRequest request, DataBufferFactory dataBufferFactory) {
        final String uriPath = request.getURI().getPath();
        final String methodName = request.getMethod().name();
        final String traceId = request.getHeaders().getFirst(TraceConstant.X_REQ_TRACE_ID);
        return new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(final Publisher<? extends DataBuffer> body) {
                MediaType contentType = this.getDelegate().getHeaders().getContentType();
                boolean isExcel = contentType != null && contentType.toString().contains("excel");
                if (isExcel) {
                    return super.writeWith(body);
                } else if (body instanceof Flux<? extends DataBuffer> fluxBody) {
                    return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                        if (StringUtils.isNotBlank(traceId)) {
                            MDC.put(TraceConstant.X_REQ_TRACE_ID, traceId);
                        }
                        try {
                            DefaultDataBuffer joinedBuffers = new DefaultDataBufferFactory().join(dataBuffers);
                            byte[] content = new byte[joinedBuffers.readableByteCount()];
                            joinedBuffers.read(content);
                            String responseBody = new String(content, StandardCharsets.UTF_8);
                            log.info("响应信息，uriPath={}, methodName={}, responseBody={}", uriPath, methodName, responseBody);
                            return dataBufferFactory.wrap(responseBody.getBytes());
                        } finally {
                            MDC.remove(TraceConstant.X_REQ_TRACE_ID);
                        }
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
        final String traceId = request.getHeaders().getFirst(TraceConstant.X_REQ_TRACE_ID);
        return new ServerHttpRequestDecorator(request) {
            @Override
            public Flux<DataBuffer> getBody() {
                return super.getBody().publishOn(Schedulers.boundedElastic()).doOnNext(dataBuffer -> {
                    if (StringUtils.isNotBlank(traceId)) {
                        MDC.put(TraceConstant.X_REQ_TRACE_ID, traceId);
                    }
                    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                        Channels.newChannel(baos).write(dataBuffer.toByteBuffer().asReadOnlyBuffer());
                        String requestBody = baos.toString(StandardCharsets.UTF_8);
                        log.info("请求信息，uriPath={}, methodName={} , queryParams={}, requestBody={}", uriPath, methodName, queryParams, requestBody);
                    } catch (Exception e) {
                        String msg = "获取requestBody异常，uriPath=" + uriPath;
                        log.error(msg, e);
                    } finally {
                        MDC.remove(TraceConstant.X_REQ_TRACE_ID);
                    }
                }).switchIfEmpty(Flux.defer(() -> {
                    if (StringUtils.isNotBlank(traceId)) {
                        MDC.put(TraceConstant.X_REQ_TRACE_ID, traceId);
                    }
                    log.info("请求信息，uriPath={}, methodName={} , queryParams={}", uriPath, methodName, queryParams);
                    MDC.remove(TraceConstant.X_REQ_TRACE_ID);
                    return Flux.just();
                }));
            }
        };
    }
}
