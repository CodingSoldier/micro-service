package com.github.codingsoldier.common.constant;

import com.github.codingsoldier.common.enums.ResultCodeEnum;

import java.util.Set;

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

    /**
     * 上游服务通过 feign 调用下游服务
     * 下游服务已配置为自动使用 Result 包装 Controller 方法返回值，且已开启全局异常捕获器
     * 例如：
     * 下游服务接口为
     * public DemoVo demoVo(@RequestParam(value = "name", required = false) String name) {
     * <p>
     * }
     * feign接口则是
     * DemoVo demoVo(@RequestParam(value = "name", required = false) String name);
     * 当下游服务抛出异常，接口返回值不是 DemoVo，而是 Result
     * 当 Result.code 不在 NOT_CHANGE_RESPONSE_STATUS_CODE_SET 集合中。
     * ResponseBodyWrapperAdvice#beforeBodyWrite 会设置 response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
     * Feign调用HTTP 状态码不以 2 开头的请求，被 FeignErrorDecoder#decode 捕获，并抛出 FeignResultErrorException
     */
    public static final Set<ResultCodeEnum> NOT_CHANGE_RESPONSE_STATUS_CODE_SET = Set.of(ResultCodeEnum.SUCCESS);



}
