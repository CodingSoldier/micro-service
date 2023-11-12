package com.github.codingsoldier.starter.nacos.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * 打印日志配置
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "micro-service.starter.nacos")
public class NacosGracefulProperties {

    /**
     * ip白名单
     */
    // private List<String> ipWhiteList = Arrays.asList("127.0.0.1", "0:0:0:0:0:0:0:1");
    private List<String> ipWhiteList = Arrays.asList("127.0.0.1");

    public List<String> getIpWhiteList() {
        return ipWhiteList;
    }

    public void setIpWhiteList(List<String> ipWhiteList) {
        this.ipWhiteList = ipWhiteList;
    }

}
