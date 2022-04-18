package com.github.codingsoldier.example.gateway.dynamic;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.codingsoldier.common.util.objectmapper.ObjectMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * <h1>通过 nacos 下发动态路由配置, 监听 Nacos 中路由配置变更</h1>
 *
 */
@Slf4j
@Component
@DependsOn({"nacosGatewayConfig"})
public class NacosListener {

    /**
     * Nacos 配置服务
     */
    private ConfigService configService;
    private final RouteEventServiceImpl dynamicRouteService;

    public NacosListener(RouteEventServiceImpl dynamicRouteService) {
        this.dynamicRouteService = dynamicRouteService;
    }

    /**
     * <h2>Bean 在容器中构造完成之后会执行 init 方法</h2>
     */
    @PostConstruct
    public void init() {
        log.info("gateway route init....");
        try {
            // 初始化 Nacos 配置客户端
            configService = initConfigService();
            if (null == configService) {
                log.error("init config service fail");
                return;
            }

            // 通过 Nacos Config 并指定路由配置路径去获取路由配置
            String configInfo = configService.getConfig(
                    NacosGatewayConfig.NACOS_ROUTE_DATA_ID,
                    NacosGatewayConfig.NACOS_ROUTE_GROUP,
                    NacosGatewayConfig.DEFAULT_TIMEOUT
            );

            log.info("get current gateway config: [{}]", configInfo);
            TypeReference<List<RouteDefinition>> typeReference = new TypeReference<List<RouteDefinition>>() {
            };
            List<RouteDefinition> definitionList = ObjectMapperUtil.readValue(configInfo, typeReference);

            if (CollectionUtils.isNotEmpty(definitionList)) {
                for (RouteDefinition definition : definitionList) {
                    log.info("init gateway config: [{}]", definition.toString());
                    dynamicRouteService.addRouteDefinition(definition);
                }
            }

        } catch (Exception ex) {
            log.error("gateway route init has some error。", ex);
        }

        // 设置监听器
        dynamicRouteByNacosListener(NacosGatewayConfig.NACOS_ROUTE_DATA_ID,
                NacosGatewayConfig.NACOS_ROUTE_GROUP);
    }

    /**
     * <h2>初始化 Nacos Config</h2>
     */
    private ConfigService initConfigService() {

        try {
            Properties properties = new Properties();
            properties.setProperty("serverAddr", NacosGatewayConfig.NACOS_SERVER_ADDR);
            properties.setProperty("namespace", NacosGatewayConfig.NACOS_NAMESPACE);
            return configService = NacosFactory.createConfigService(properties);
        } catch (Exception ex) {
            log.error("init gateway nacos config error。", ex);
            return null;
        }
    }

    /**
     * <h2>监听 Nacos 下发的动态路由配置</h2>
     */
    private void dynamicRouteByNacosListener(String dataId, String group) {

        try {
            // 给 Nacos Config 客户端增加一个监听器
            configService.addListener(dataId, group, new Listener() {

                /**
                 * <h2>可以自己提供线程池执行操作,这里使用默认的</h2>
                 * */
                @Override
                public Executor getExecutor() {
                    return null;
                }

                /**
                 * <h2>监听器收到配置更新</h2>
                 * @param configInfo Nacos 中最新的配置定义
                 * */
                @Override
                public void receiveConfigInfo(String configInfo) {

                    log.info("start to update config: [{}]", configInfo);
                    TypeReference<List<RouteDefinition>> typeReference = new TypeReference<List<RouteDefinition>>() {
                    };
                    List<RouteDefinition> definitionList = ObjectMapperUtil.readValue(configInfo, typeReference);
                    log.info("update route: [{}]", definitionList.toString());
                    dynamicRouteService.updateList(definitionList);
                }
            });
        } catch (NacosException ex) {
            log.error("dynamic update gateway config error。", ex);
        }
    }
}
