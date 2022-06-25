package com.github.codingsoldier.starter.web.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * feign okhttp配置
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@ConfigurationProperties(prefix = "framework.starter.web.logging")
public class RequestLoggingProperties {

    /**
     * 是否打印request、responseBody日志。
     */
    private boolean requestResponseBodyLog = false;

    /**
     * 是否打印request日志，requestResponseBodyLog、requestLog 有一个是true，就打印request日志
     */
    private boolean requestLog = false;

    /**
     * 是否打印responseBody日志，requestResponseBodyLog、responseBodyLog 有一个是true，就打印responseBody日志
     */
    private boolean responseBodyLog = false;

    /**
     * 是否包含客户端信息
     */
    private boolean includeClientInfo = false;

    /**
     * 是否包含请求头信息
     */
    private boolean includeHeaders = false;

    /**
     * 是否包含请求查询字符串
     */
    private boolean includeQueryString = true;

    /**
     * 是否包含body
     */
    private boolean includePayload = true;

    /**
     * body最大长度
     */
    private int maxPayloadLength = 2048;


    public boolean isRequestResponseBodyLog() {
        return requestResponseBodyLog;
    }

    public void setRequestResponseBodyLog(boolean requestResponseBodyLog) {
        this.requestResponseBodyLog = requestResponseBodyLog;
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

    public boolean isIncludeClientInfo() {
        return includeClientInfo;
    }

    public void setIncludeClientInfo(boolean includeClientInfo) {
        this.includeClientInfo = includeClientInfo;
    }

    public boolean isIncludeHeaders() {
        return includeHeaders;
    }

    public void setIncludeHeaders(boolean includeHeaders) {
        this.includeHeaders = includeHeaders;
    }

    public boolean isIncludeQueryString() {
        return includeQueryString;
    }

    public void setIncludeQueryString(boolean includeQueryString) {
        this.includeQueryString = includeQueryString;
    }

    public boolean isIncludePayload() {
        return includePayload;
    }

    public void setIncludePayload(boolean includePayload) {
        this.includePayload = includePayload;
    }

    public int getMaxPayloadLength() {
        return maxPayloadLength;
    }

    public void setMaxPayloadLength(int maxPayloadLength) {
        this.maxPayloadLength = maxPayloadLength;
    }
}
