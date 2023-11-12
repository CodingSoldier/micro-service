package com.github.codingsoldier.example.cloudweb01.nacos;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.registry.NacosRegistration;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
public class NacosGraceful {
    @Autowired
    private NacosServiceManager nacosServiceManager;
    @Autowired
    private NacosRegistration nacosRegistration;

    /**
     * 启动nacos客户端，启动完成便可以接收请求流量
     * @throws NacosException
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
        int port = nacosDiscoveryProperties.getPort();
        String clusterName = nacosDiscoveryProperties.getClusterName();
        nacosServiceManager.getNamingService(nacosProperties).registerInstance(service, group, ip, port, clusterName);
        log.info("nacos客户端实例启动成功");
    }

    /**
     * 注销nacos客户端
     * @throws NacosException
     */
    public void deregisterInstance() throws NacosException {
        NacosDiscoveryProperties nacosDiscoveryProperties = nacosRegistration.getNacosDiscoveryProperties();
        Properties nacosProperties = nacosDiscoveryProperties.getNacosProperties();
        String service = nacosDiscoveryProperties.getService();
        String group = nacosDiscoveryProperties.getGroup();
        String ip = nacosDiscoveryProperties.getIp();
        int port = nacosDiscoveryProperties.getPort();
        String clusterName = nacosDiscoveryProperties.getClusterName();
        nacosServiceManager.getNamingService(nacosProperties).deregisterInstance(service, group, ip, port, clusterName);
        log.info("nacos客户端实例注销成功");
    }

}
