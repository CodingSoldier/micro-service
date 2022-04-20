package com.github.codingsoldier.starter.openfeign.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "framework.starter.openfeign.okhttp")
public class OkHttpClientProperties {

    /**
     * 连接超时
     */
    private Long connectTimeoutSeconds = 3L;
    /**
     * 读超时
     */
    private Long readTimeoutSeconds = 30L;
    /**
     * 写超时
     */
    private Long writeTimeoutSeconds = 30L;

    /**
     * 是否自动重连
     */
    private Boolean retryOnConnectionFailure = true;

    /**
     * 连接池
     */
    private Pool pool = new Pool();


    public Long getConnectTimeoutSeconds() {
        return connectTimeoutSeconds;
    }

    public void setConnectTimeoutSeconds(Long connectTimeoutSeconds) {
        this.connectTimeoutSeconds = connectTimeoutSeconds;
    }

    public Long getReadTimeoutSeconds() {
        return readTimeoutSeconds;
    }

    public void setReadTimeoutSeconds(Long readTimeoutSeconds) {
        this.readTimeoutSeconds = readTimeoutSeconds;
    }

    public Long getWriteTimeoutSeconds() {
        return writeTimeoutSeconds;
    }

    public void setWriteTimeoutSeconds(Long writeTimeoutSeconds) {
        this.writeTimeoutSeconds = writeTimeoutSeconds;
    }

    public Boolean getRetryOnConnectionFailure() {
        return retryOnConnectionFailure;
    }

    public void setRetryOnConnectionFailure(Boolean retryOnConnectionFailure) {
        this.retryOnConnectionFailure = retryOnConnectionFailure;
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    /**
     * Pool properties.
     */
    public static class Pool {
        /**
         * 连接池中的最大空闲线程个数
         */
        private Integer maxIdleConnections = 50;
        /**
         * 空间持续时间
         */
        private Long keepAliveDurationSeconds = 5*60L;

        public Integer getMaxIdleConnections() {
            return maxIdleConnections;
        }

        public void setMaxIdleConnections(Integer maxIdleConnections) {
            this.maxIdleConnections = maxIdleConnections;
        }

        public Long getKeepAliveDurationSeconds() {
            return keepAliveDurationSeconds;
        }

        public void setKeepAliveDurationSeconds(Long keepAliveDurationSeconds) {
            this.keepAliveDurationSeconds = keepAliveDurationSeconds;
        }
    }

}
