package com.github.codingsoldier.starter.web.config;

import com.github.codingsoldier.starter.web.interceptor.FeignInterceptor;
import com.github.codingsoldier.starter.web.properties.CorsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.temporal.ChronoUnit;

/**
 * WebMvcConfigurer 配置
 * @author chenpq05
 * @since 2022/2/11 12:02
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(value = "micro-service.starter.web.enableWebMvcConfig", matchIfMissing = true)
public class WebMvcConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    private final CorsProperties corsProperties;

    public WebMvcConfig(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 是否允许证书（cookies）
                .allowCredentials(this.corsProperties.getAllowCredentials())
                // 设置允许跨域请求的域名
                // SpringBoot2.4.0 [allowedOriginPatterns]代替[allowedOrigins]
                .allowedOriginPatterns(this.corsProperties.getAllowedOriginPatterns().toArray(String[]::new))
                // 设置允许的方法
                .allowedMethods(this.corsProperties.getAllowedMethods().toArray(String[]::new))
                .allowedHeaders(this.corsProperties.getAllowedHeaders().toArray(String[]::new))
                // 跨域允许时间
                .maxAge(this.corsProperties.getMaxAge().get(ChronoUnit.SECONDS));
    }

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
