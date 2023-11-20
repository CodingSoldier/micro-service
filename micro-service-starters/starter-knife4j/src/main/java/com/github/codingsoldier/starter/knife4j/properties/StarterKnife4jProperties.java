package com.github.codingsoldier.starter.knife4j.properties;

import com.github.codingsoldier.starter.knife4j.config.Knife4jConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * starter-spring-doc 配置
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@ConfigurationProperties(prefix = "micro-service.starter.spring-doc")
public class StarterKnife4jProperties {

    /**
     * 基础扫描路径
     */
    private String title = "API文档";

    /**
     * 基础扫描路径
     */
    private String basePackage = Knife4jConfiguration.BASE_PACKAGE;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

}
