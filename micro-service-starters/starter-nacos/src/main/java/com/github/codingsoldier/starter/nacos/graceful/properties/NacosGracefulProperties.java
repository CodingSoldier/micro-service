package com.github.codingsoldier.starter.nacos.graceful.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * nacos灰度发布配置
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "micro-service.starter.nacos.graceful")
public class NacosGracefulProperties {

    /**
     * 可以调用nacos客户端下线接口的ip白名单列表，支持正则
     */
     private List<String> ipWhiteList = Arrays.asList("127.0.0.1", "0:0:0:0:0:0:0:1");

    /**
     * nacos客户端在系统启动后，延迟注册的时间
     */
    private long delayRegisterMilliseconds = 1000;

    public List<String> getIpWhiteList() {
        return ipWhiteList;
    }

    public void setIpWhiteList(List<String> ipWhiteList) {
        this.ipWhiteList = ipWhiteList;
    }

    public long getDelayRegisterMilliseconds() {
        return delayRegisterMilliseconds;
    }

    public void setDelayRegisterMilliseconds(long delayRegisterMilliseconds) {
        this.delayRegisterMilliseconds = delayRegisterMilliseconds;
    }
}
