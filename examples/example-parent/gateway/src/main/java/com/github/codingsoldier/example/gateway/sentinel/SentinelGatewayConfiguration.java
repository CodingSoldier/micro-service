package com.github.codingsoldier.example.gateway.sentinel;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import com.github.codingsoldier.common.enums.ResponseCodeEnum;
import com.github.codingsoldier.common.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

/**
 * Gateway 集成 Sentinel 实现限流
 */
@Slf4j
@Configuration
public class SentinelGatewayConfiguration {

    /**
     * 视图解析器
     */
    private final List<ViewResolver> viewResolvers;
    /**
     * HTTP 请求和响应数据的编解码配置
     */
    private final ServerCodecConfigurer serverCodecConfigurer;

    /**
     * 构造方法
     */
    public SentinelGatewayConfiguration(
            ObjectProvider<List<ViewResolver>> viewResolversProvider,
            ServerCodecConfigurer serverCodecConfigurer
    ) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    /**
     * <h2>限流异常处理器, 限流异常出现时, 执行到这个 handler</h2>
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        // 默认会返回错误 message, code 429
        return new SentinelGatewayBlockExceptionHandler(
                this.viewResolvers,
                this.serverCodecConfigurer
        );
    }

    @PostConstruct
    public void doInit() {
        // 加载自定义限流异常处理器
        initBlockHandler();
    }

    /**
     * 自定义限流异常处理器
     */
    private void initBlockHandler() {

        // 自定义 BlockRequestHandler
        BlockRequestHandler blockRequestHandler = new BlockRequestHandler() {
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange,
                                                      Throwable throwable) {

                Result result = Result.fail(ResponseCodeEnum.TOO_MANY_REQUESTS.getCode(), ResponseCodeEnum.TOO_MANY_REQUESTS.getMessage());

                return ServerResponse
                        .status(HttpStatus.TOO_MANY_REQUESTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(result));
            }
        };

        // 设置自定义限流异常处理器
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }

}
