package com.github.codingsoldier.common.constant;

/**
 * feign常量
 *
 * @author chenpq
 * @since 2022/2/8 14:19
 */
public class FeignConstant {

    private FeignConstant() {
        // sonar检测
        throw new IllegalStateException("不允许实例化");
    }

    /**
     * 是否 feign 请求
     */
    public static final String IS_FEIGN_REQUEST = "is-feign-request";

}
