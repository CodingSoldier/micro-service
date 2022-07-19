package com.github.codingsoldier.example.gateway.filter;

import com.github.codingsoldier.example.gateway.util.SleuthLogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * 打印请求日志
 */
@Slf4j
@Component
public class LogRequestFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        final ServerHttpRequest request = exchange.getRequest();
        String methodValue = request.getMethodValue();
        String path = request.getURI().getPath();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        MediaType mediaType = request.getHeaders().getContentType();
        boolean isPrintBody = (Objects.isNull(mediaType)
                || MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(mediaType)
                || MediaType.APPLICATION_JSON.isCompatibleWith(mediaType))
                && HttpMethod.POST.matches(methodValue);
        if ("不打印请求参数的URI".equals(path)){
            return chain.filter(exchange);
        } else if (isPrintBody) {
            Flux<DataBuffer> fluxBody = request.getBody();
            // DataBufferUtils.join() 获取 Flux<DataBuffer> 中的 DataBuffer
            return DataBufferUtils.join(fluxBody)
                .flatMap(dataBuffer -> {
                    // 调用一次 retain 方法，缓冲区的引用计数加1，引用计数大于1，确保数据缓冲区不被释放
                    // DataBufferUtils.retain(dataBuffer);

                    // slice方法：做数据分割，将当前的position到limit之间的数据分割出来，返回一个新的ByteBuffer,
                    // 同时,mark标记重置为-1。
                    DataBuffer bodyDataBuffer = dataBuffer.slice(0, dataBuffer.readableByteCount());
                    // defer、just 都是创建数据源，得到当前数据的副本
                    Flux<DataBuffer> cacheFlux = Flux.defer(() -> Flux.just(bodyDataBuffer));
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
                    String logData = new String(bodyByte, StandardCharsets.UTF_8);
                    // 打印日志
                    printLog(exchange, path, methodValue, queryParams, logData);
                    DataBufferUtils.release(dataBuffer);
                    return chain.filter(newExchange);
                });
        }
        // 打印日志
        printLog(exchange, path, methodValue, queryParams, null);
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
     * @param method
     * @param requestBody
     */
    private void printLog(ServerWebExchange exchange, String requestURI, String method,
                          MultiValueMap<String, String> requestParam, String requestBody) {
        LinkedHashMap<String, Object> logData = new LinkedHashMap<>();
        logData.put("method", method);
        logData.put("requestURI", requestURI);
        logData.put("requestParam", requestParam);
        if (StringUtils.isNotBlank(requestBody)) {
            logData.put("requestBody", requestBody);
        }
        // 添加traceId，非常耗性能
        SleuthLogUtil.log(exchange, () -> log.info("网关打印request数据。{}", logData));
    }

}
