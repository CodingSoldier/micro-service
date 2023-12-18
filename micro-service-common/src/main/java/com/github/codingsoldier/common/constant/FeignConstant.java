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
     * feign服务提供方，方法返回值
     */
    public static final String PROVIDER_FUNCTION_RETURN_TYPE = "x-pfry";

    /**
     * 是否 feign 请求
     */
    public static final String IS_FEIGN_REQUEST = "is-feign-request";

}
