package com.github.codingsoldier.starter.openfeign.config;

import com.github.codingsoldier.starter.openfeign.properties.OkHttpClientProperties;
import feign.Feign;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * OpenFeign 使用 OkHttp 配置类
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(Feign.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
@EnableConfigurationProperties(OkHttpClientProperties.class)
public class FeignOkHttpConfig {

    /**
     * <h2>注入 OkHttp, 并自定义配置</h2>
     */
    @Bean
    public OkHttpClient okHttpClient(OkHttpClientProperties okHttpClientProperties) {
        return new OkHttpClient.Builder()
                // 设置连接超时
                .connectTimeout(okHttpClientProperties.getConnectTimeoutSeconds(), TimeUnit.SECONDS)
                // 设置读超时
                .readTimeout(okHttpClientProperties.getReadTimeoutSeconds(), TimeUnit.SECONDS)
                // 设置写超时
                .writeTimeout(okHttpClientProperties.getWriteTimeoutSeconds(), TimeUnit.SECONDS)
                // 是否自动重连
                .retryOnConnectionFailure(okHttpClientProperties.getRetryOnConnectionFailure())
                // 配置连接池中的最大空闲线程个数、空间持续时间
                .connectionPool(new ConnectionPool(
                        okHttpClientProperties.getPool().getMaxIdleConnections(),
                        okHttpClientProperties.getPool().getKeepAliveDurationSeconds(),
                        TimeUnit.SECONDS))
                .build();
    }
}
