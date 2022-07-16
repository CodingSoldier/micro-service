package com.github.codingsoldier.example.gateway.filter;

import com.github.codingsoldier.common.util.objectmapper.ObjectMapperUtil;
import com.github.codingsoldier.example.gateway.log.SleuthLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存请求body全局过滤器
 */
@Slf4j
@Component
public class LogFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        if ("不打印请求参数的URI".equals(path)) {
            return chain.filter(exchange);
        } else if (HttpMethod.POST.matches(request.getMethodValue())) {
            Flux<DataBuffer> fluxBody = request.getBody();
            // DataBufferUtils.join() 获取 Flux<DataBuffer> 中的 DataBuffer
            return DataBufferUtils.join(fluxBody)
                .flatMap(dataBuffer -> {
                    // 确保数据缓冲区不被释放
                    DataBufferUtils.retain(dataBuffer);
                    DataBuffer bodyDataBuffer = dataBuffer.slice(0, dataBuffer.readableByteCount());
                    // defer、just 都是创建数据源，得到当前数据的副本
                    Flux<DataBuffer> cacheFlux = Flux.defer(() ->
                            Flux.just(bodyDataBuffer));
                    // 重新包装 ServerHttpRequest ，重写 getBody() 返回请求数据
                    ServerHttpRequestDecorator mutateRequest = new ServerHttpRequestDecorator(request) {
                        @Override
                        public Flux<DataBuffer> getBody() {
                            return cacheFlux;
                        }
                    };
                    // 将新包装的 ServerWebExchange 向下传递
                    ServerWebExchange newExchange = exchange.mutate().request(mutateRequest).build();

                    // 获取body
                    DataBuffer logDataBuffer = dataBuffer.slice(0, dataBuffer.readableByteCount());
                    byte[] bodyByte = new byte[dataBuffer.readableByteCount()];
                    logDataBuffer.read(bodyByte);
                    Map<String, Object> bodyMap = new HashMap<>();
                    try {
                        bodyMap = ObjectMapperUtil.readValue(bodyByte, Map.class);
                    } catch (Exception e) {
                        log.error("读取body缓存异常", e);
                    }
                    // 打印日志
                    printLog(exchange, path, queryParams, bodyMap);

                    return chain.filter(newExchange);
                });
        }
        // 打印日志
        printLog(exchange, path, queryParams, null);
        return chain.filter(exchange);
    }

    // order值越小越先执行
    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 1;
    }

    /**
     * 打印日志
     * @param exchange
     * @param requestURI
     * @param requestParam
     * @param body
     */
    private void printLog(ServerWebExchange exchange, String requestURI,
                          MultiValueMap<String, String> requestParam, Map<String, Object> body) {
        HashMap<String, Object> logData = new HashMap<>();
        logData.put("requestURI", requestURI);
        logData.put("requestParam", requestParam);
        logData.put("body", body);
        // 添加traceId，非常耗性能
        SleuthLog.log(exchange, () -> log.info("网关打印request数据，{}", logData));
    }

}
