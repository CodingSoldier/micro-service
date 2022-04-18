package com.github.codingsoldier.example.cloudweb02.controller;

import feign.Feign;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * <h1>OpenFeign 使用 OkHttp 配置类</h1>
 */
@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
public class FeignOkHttpConfig {

    /**
     * <h2>注入 OkHttp, 并自定义配置</h2>
     */
    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                // 设置连接超时
                .connectTimeout(5, TimeUnit.SECONDS)
                // 设置读超时
                .readTimeout(50, TimeUnit.SECONDS)
                // 设置写超时
                .writeTimeout(50, TimeUnit.SECONDS)
                // 是否自动重连
                .retryOnConnectionFailure(true)
                // 配置连接池中的最大空闲线程个数、空间持续时间
                .connectionPool(new ConnectionPool(
                        50, 5L, TimeUnit.MINUTES))
                .build();
    }
}
