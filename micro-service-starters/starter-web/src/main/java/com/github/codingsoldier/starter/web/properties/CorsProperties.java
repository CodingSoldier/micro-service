package com.github.codingsoldier.starter.web.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

/**
 * cors配置
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "micro-service.starter.web.cors")
public class CorsProperties {

    private Boolean allowCredentials = true;

    private List<String> allowedOriginPatterns = List.of("*");

    private List<String> allowedHeaders = List.of("*");

    private List<String> allowedMethods = List.of("*");

    private Duration maxAge = Duration.ofSeconds(1800);

    public Boolean getAllowCredentials() {
        return allowCredentials;
    }

    public void setAllowCredentials(Boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }

    public List<String> getAllowedOriginPatterns() {
        return allowedOriginPatterns;
    }

    public void setAllowedOriginPatterns(List<String> allowedOriginPatterns) {
        this.allowedOriginPatterns = allowedOriginPatterns;
    }

    public List<String> getAllowedHeaders() {
        return allowedHeaders;
    }

    public void setAllowedHeaders(List<String> allowedHeaders) {
        this.allowedHeaders = allowedHeaders;
    }

    public List<String> getAllowedMethods() {
        return allowedMethods;
    }

    public void setAllowedMethods(List<String> allowedMethods) {
        this.allowedMethods = allowedMethods;
    }

    public Duration getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Duration maxAge) {
        this.maxAge = maxAge;
    }
}
