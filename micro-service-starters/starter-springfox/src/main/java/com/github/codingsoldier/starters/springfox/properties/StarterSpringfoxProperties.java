package com.github.codingsoldier.starters.springfox.properties;

import com.github.codingsoldier.starters.springfox.SpringfoxConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * StarterSwagger配置
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@ConfigurationProperties(prefix = "micro-service.starter.springfox")
public class StarterSpringfoxProperties {

    /**
     * 基础扫描路径
     */
    private String basePackage = SpringfoxConfig.BASE_PACKAGE;

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

}
