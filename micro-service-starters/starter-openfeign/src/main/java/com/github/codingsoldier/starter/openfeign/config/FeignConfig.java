package com.github.codingsoldier.starter.openfeign.config;

import com.github.codingsoldier.common.feign.FeignConstant;
import com.github.codingsoldier.starter.openfeign.codec.FeignErrorDecoder;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenFeign 配置类
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Configuration(proxyBeanMethods = false)
public class FeignConfig {

    /**
     * 拦截器，feign请求加上请求头
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header(FeignConstant.IS_FEIGN_REQUEST, Boolean.TRUE.toString());
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }

}
