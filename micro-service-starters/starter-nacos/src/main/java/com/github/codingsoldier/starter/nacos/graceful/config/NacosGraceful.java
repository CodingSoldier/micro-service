package com.github.codingsoldier.starter.nacos.graceful.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.registry.NacosRegistration;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * nacos客户单优雅发布核心类
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Slf4j
@Component
public class NacosGraceful {

    @Value("${server.port}")
    private int port;

    private final NacosServiceManager nacosServiceManager;
    private final NacosRegistration nacosRegistration;

    public NacosGraceful(NacosServiceManager nacosServiceManager, NacosRegistration nacosRegistration) {
        this.nacosServiceManager = nacosServiceManager;
        this.nacosRegistration = nacosRegistration;
    }

    /**
     * 启动nacos客户端，启动完成便可以接收请求流量
     * @throws NacosException NacosException
     */
    public void registerInstance() throws NacosException {
        NacosDiscoveryProperties nacosDiscoveryProperties = nacosRegistration.getNacosDiscoveryProperties();
        if (nacosDiscoveryProperties.isInstanceEnabled()) {
            log.debug("nacos客户的配置instance-enabled=true，不需要再次注册");
            return;
        }
        Properties nacosProperties = nacosDiscoveryProperties.getNacosProperties();
        String service = nacosDiscoveryProperties.getService();
        String group = nacosDiscoveryProperties.getGroup();
        String ip = nacosDiscoveryProperties.getIp();
        int discoveryPort = nacosDiscoveryProperties.getPort();
        if (discoveryPort == -1) {
            discoveryPort = port;
        }
        String clusterName = nacosDiscoveryProperties.getClusterName();
        nacosServiceManager.getNamingService(nacosProperties).registerInstance(service, group, ip, discoveryPort, clusterName);
        log.info("nacos客户端实例启动成功");
    }

    /**
     * 注销nacos客户端
     * @throws NacosException NacosException
     */
    public void deregisterInstance() throws NacosException {
        NacosDiscoveryProperties nacosDiscoveryProperties = nacosRegistration.getNacosDiscoveryProperties();
        Properties nacosProperties = nacosDiscoveryProperties.getNacosProperties();
        String service = nacosDiscoveryProperties.getService();
        String group = nacosDiscoveryProperties.getGroup();
        String ip = nacosDiscoveryProperties.getIp();
        int discoveryPort = nacosDiscoveryProperties.getPort();
        if (discoveryPort == -1) {
            discoveryPort = port;
        }
        String clusterName = nacosDiscoveryProperties.getClusterName();
        nacosServiceManager.getNamingService(nacosProperties).deregisterInstance(service, group, ip, discoveryPort, clusterName);
        log.info("nacos客户端实例注销成功");
    }

}
