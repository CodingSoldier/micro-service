package com.github.codingsoldier.example.cloudweb01.nacos;

import com.github.codingsoldier.common.exception.ClientException;
import com.github.codingsoldier.example.cloudweb01.nacos.constant.Constant;
import com.github.codingsoldier.example.cloudweb01.nacos.properties.NacosGracefulProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
public class NacosApiInterceptor implements HandlerInterceptor {

    private NacosGracefulProperties nacosGracefulProperties;

    public NacosApiInterceptor(NacosGracefulProperties nacosGracefulProperties) {
        this.nacosGracefulProperties = nacosGracefulProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String ipAddr = IpUtil.getIpAddr(request);
        if (StringUtils.isEmpty(ipAddr)) {
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