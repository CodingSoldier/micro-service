package com.github.codingsoldier.common.constant;

/**
 * 链路追踪常量
 *
 * @author chenpq
 * @since 2022/2/8 14:19
 */
public class TraceConstant {

    private TraceConstant() {
        // sonar
    }

    /**
     * 自定义链路追踪请求头
     */
    public static final String X_REQ_TRACE_ID = "x-req-trace-id";

}
