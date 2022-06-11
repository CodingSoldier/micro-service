package com.github.codingsoldier.example.gateway.dynamic;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 承载nacos配置信息
 */
@SuppressWarnings("squid:S2696")
@Configuration(proxyBeanMethods = false)
@Data
public class NacosGatewayProperties {
    /**
     * 读取配置的超时时间
     */
    public static final long DEFAULT_TIMEOUT = 30000;

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String nacosServerAddr;

    @Value("${spring.cloud.nacos.discovery.namespace}")
    public String nacosNamespace;

    @Value("${nacos.gateway.route.config.data-id}")
    public String nacosRouteDataId;

    @Value("${nacos.gateway.route.config.group}")
    public String nacosRouteGroup;

}
