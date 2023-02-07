package com.github.codingsoldier.starters.springdoc.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * starter-spring-doc 配置
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@ConfigurationProperties(prefix = "micro-service.starter.spring-doc")
public class StarterSpringDocProperties {

    /**
     * 基础扫描路径
     */
    private String title = "API文档";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
