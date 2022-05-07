package com.github.codingsoldier.starters.wagger.properties;

import com.github.codingsoldier.starters.wagger.SwaggerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * StarterSwagger配置
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@ConfigurationProperties(prefix = "framework.starter.swagger")
public class StarterSwaggerProperties {

    /**
     * 基础扫描路径
     */
    private String basePackage = SwaggerConfig.BASE_PACKAGE;

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

}
