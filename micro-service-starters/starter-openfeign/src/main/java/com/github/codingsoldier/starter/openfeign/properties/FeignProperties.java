package com.github.codingsoldier.starter.openfeign.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * feign okhttp配置
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@ConfigurationProperties(prefix = "framework.starter.openfeign")
public class FeignProperties {

    /**
     * 日志级别，可选值：NONE、BASIC、HEADERS、FULL。可参考 feign.Logger.Level
     *
     */
    private String logLevel = "BASIC";

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }
}
