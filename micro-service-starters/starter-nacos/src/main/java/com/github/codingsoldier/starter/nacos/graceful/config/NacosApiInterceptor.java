package com.github.codingsoldier.starter.nacos.graceful.config;

import com.github.codingsoldier.common.exception.ClientException;
import com.github.codingsoldier.starter.nacos.graceful.constant.Constant;
import com.github.codingsoldier.starter.nacos.graceful.properties.NacosGracefulProperties;
import com.github.codingsoldier.starter.nacos.util.IpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class NacosApiInterceptor implements HandlerInterceptor {
    private NacosGracefulProperties nacosGracefulProperties;
    public NacosApiInterceptor(NacosGracefulProperties nacosGracefulProperties) {
        this.nacosGracefulProperties = nacosGracefulProperties;
    }

    /**
     * 拦截nacos客户端下线api
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String ipAddr = IpUtil.getIpAddr(request);
        if (StringUtils.isBlank(ipAddr)) {
            log.warn("获取IP失败");
            return true;
        }
        String requestURI = request.getRequestURI();
        if (!requestURI.endsWith(Constant.URI_DEREGISTER_INSTANCE)) {
            return true;
        }

        boolean isWhiteIp = false;
        List<String> ipWhiteList = nacosGracefulProperties.getIpWhiteList();
        if (!CollectionUtils.isEmpty(ipWhiteList)) {
            for (String whiteIp : ipWhiteList) {
                boolean matcher = Pattern.matches(whiteIp, ipAddr);
                if (matcher) {
                    isWhiteIp = true;
                    break;
                }
            }
        }
        if (!isWhiteIp) {
            throw new ClientException("ip不在白名单内，无权访问此API。");
        }
        return true;
    }
}