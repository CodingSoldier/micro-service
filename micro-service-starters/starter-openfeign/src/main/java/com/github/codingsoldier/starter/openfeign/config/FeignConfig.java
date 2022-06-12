package com.github.codingsoldier.starter.openfeign.config;

import com.github.codingsoldier.common.feign.FeignConstant;
import com.github.codingsoldier.starter.openfeign.codec.FeignErrorDecoder;
import feign.Logger;
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
@SuppressWarnings("squid:S125")
@Configuration(proxyBeanMethods = false)
public class FeignConfig {

    /**
     * 拦截器，feign请求加上请求头
     *
     * @return FEIGN_REQUEST
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header(FeignConstant.FEIGN_REQUEST, Boolean.TRUE.toString());
    }


    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }

    /**
     * 开启 OpenFeign 日志
     */
    @Bean
    public Logger.Level feignLogger() {
        //  需要注意, 日志级别需要修改成 debug
        return Logger.Level.FULL;
    }


    //
    // /**
    //  * <h2>OpenFeign 开启重试</h2>
    //  * period = 100 发起当前请求的时间间隔, 单位是 ms
    //  * maxPeriod = 1000 发起当前请求的最大时间间隔, 单位是 ms
    //  * maxAttempts = 5 最多请求次数
    //  */
    // @Bean
    // public Retryer feignRetryer() {
    //     return new Retryer.Default(
    //             100,
    //             SECONDS.toMillis(1),
    //             5
    //     );
    // }
    //
    // public static final int CONNECT_TIMEOUT_MILLS = 5000;
    // public static final int READ_TIMEOUT_MILLS = 5000;
    //
    // /**
    //  * <h2>对请求的连接和响应时间进行限制</h2>
    //  */
    // @Bean
    // public Request.Options options() {
    //
    //     return new Request.Options(
    //             CONNECT_TIMEOUT_MILLS, TimeUnit.MICROSECONDS,
    //             READ_TIMEOUT_MILLS, TimeUnit.MILLISECONDS,
    //             true
    //     );
    // }
}
