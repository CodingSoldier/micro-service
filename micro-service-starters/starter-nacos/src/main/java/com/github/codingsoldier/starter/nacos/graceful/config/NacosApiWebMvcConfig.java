package com.github.codingsoldier.starter.nacos.graceful.config;

import com.github.codingsoldier.starter.nacos.graceful.properties.NacosGracefulProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfigurer 配置
 * @author chenpq05
 * @since 2022/2/11 12:02
 */
@Order
@Slf4j
@Configuration(proxyBeanMethods = false)
public class NacosApiWebMvcConfig implements WebMvcConfigurer {

    private final NacosGracefulProperties nacosGracefulProperties;

    public NacosApiWebMvcConfig(NacosGracefulProperties nacosGracefulProperties) {
        this.nacosGracefulProperties = nacosGracefulProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (log.isDebugEnabled()) {
            log.debug("添加NacosApiWebMvcConfig");
        }
        registry.addInterceptor(new NacosApiInterceptor(nacosGracefulProperties))
                .addPathPatterns("/**");
    }

}
