package com.github.codingsoldier.starter.web.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 打印日志配置
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "framework.starter.web.logging")
public class LoggingProperties {

    /**
     * 是否打印request、responseBody日志。
     */
    private boolean requestResponseLog = false;

    /**
     * 是否打印request日志，requestResponseLog、requestLog 有一个是true，就打印request日志
     */
    private boolean requestLog = false;

    /**
     * 是否打印responseBody日志，requestResponseBodyLog、responseBodyLog 有一个是true，就打印responseBody日志
     */
    private boolean responseBodyLog = false;

    /**
     * 是否包含请求头信息
     */
    private boolean includeHeaders = false;

    public boolean isRequestResponseLog() {
        return requestResponseLog;
    }

    public void setRequestResponseLog(boolean requestResponseLog) {
        this.requestResponseLog = requestResponseLog;
    }

    public boolean isRequestLog() {
        return requestLog;
    }

    public void setRequestLog(boolean requestLog) {
        this.requestLog = requestLog;
    }

    public boolean isResponseBodyLog() {
        return responseBodyLog;
    }

    public void setResponseBodyLog(boolean responseBodyLog) {
        this.responseBodyLog = responseBodyLog;
    }

    public boolean isIncludeHeaders() {
        return includeHeaders;
    }

    public void setIncludeHeaders(boolean includeHeaders) {
        this.includeHeaders = includeHeaders;
    }
}
