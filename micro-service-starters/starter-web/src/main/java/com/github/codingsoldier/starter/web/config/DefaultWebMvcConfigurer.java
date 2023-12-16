package com.github.codingsoldier.starter.web.config;

import com.github.codingsoldier.starter.web.interceptor.FeignInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfigurer 配置
 * @author chenpq05
 * @since 2022/2/11 12:02
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(value = "micro-service.starter.web.enableDefaultWebMvcConfigurer", matchIfMissing = true)
public class DefaultWebMvcConfigurer implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(DefaultWebMvcConfigurer.class);

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (logger.isDebugEnabled()) {
            logger.debug("添加FeignInterceptor");
        }
        //定义排除访问的路径配置
        String[] excludePaths = new String[]{"/swagger-ui.html", "/swagger-ui/**",
            "/doc.html", "/v3/api-docs/**",
            "/error",
            "/actuator/**"
        };
        registry.addInterceptor(new FeignInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludePaths);
    }

}
