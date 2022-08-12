package com.github.codingsoldier.example.bootweb.config;

import com.github.codingsoldier.example.bootweb.filter.WebHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author chenpq05
 * @since 2022/2/11 12:02
 */
@Configuration(proxyBeanMethods = false)
public class BootWebMvcConfig implements WebMvcConfigurer {

    /**
     * 拦截器配置
     *
     * @param registry registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //定义排除访问的路径配置
        String[] excludePaths = new String[]{"/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
                "/error",
                "/actuator/**",
                "/open/api/**"
        };

        registry.addInterceptor(new WebHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludePaths);
    }


}
