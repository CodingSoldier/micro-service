package com.github.codingsoldier.starter.web.filter;

import com.github.codingsoldier.starter.web.properties.RequestLoggingProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;

@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(RequestLoggingProperties.class)
public class RequestLoggingFilter extends CommonsRequestLoggingFilter{

    private RequestLoggingProperties properties;

    public RequestLoggingFilter(RequestLoggingProperties properties) {
        super();
        this.properties = properties;
        super.setIncludeClientInfo(properties.isIncludeClientInfo());
        super.setIncludeHeaders(properties.isIncludeHeaders());
        super.setIncludeQueryString(properties.isIncludeQueryString());
        super.setIncludePayload(properties.isIncludePayload());
        super.setMaxPayloadLength(properties.getMaxPayloadLength());
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        if (properties.isRequestResponseBodyLog() || properties.isRequestLog()) {
            log.info("打印Request信息={}", message);
        }
    }

}
