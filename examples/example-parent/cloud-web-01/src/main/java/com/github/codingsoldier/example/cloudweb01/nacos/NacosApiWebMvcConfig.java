package com.github.codingsoldier.example.cloudweb01.nacos;

import com.github.codingsoldier.example.cloudweb01.nacos.properties.NacosGracefulProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfigurer 配置
 * @author chenpq05
 * @since 2022/2/11 12:02
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class NacosApiWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private NacosGracefulProperties nacosGracefulProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (log.isDebugEnabled()) {
            log.debug("添加NacosApiWebMvcConfig");
        }
        registry.addInterceptor(new NacosApiInterceptor(nacosGracefulProperties))
                .addPathPatterns("/**");
    }





}
