package com.github.codingsoldier.common.feign;

import com.github.codingsoldier.common.enums.ResponseCodeEnum;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * feign常量
 *
 * @author chenpq
 * @since 2022/2/8 14:19
 */
public class FeignConstant {

    /**
     * 是否 feign 请求
     */
    public static final String FEIGN_REQUEST = "feign-request";
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
     * 当 Result.code 不在 NOT_CHANGE_RESPONSE_STATUS_CODE_SET 集合中
     *
     * @see com.github.codingsoldier.starter.web.advice.ResponseBodyWrapperAdvice#beforeBodyWrite 会执行
     * response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
     * HTTP 状态码不以 2 开头的请求，被
     * @see com.github.codingsoldier.starter.openfeign.codec.FeignErrorDecoder#decode
     * 捕获，并抛出 ResultNotSuccessFeignException
     */
    @SuppressWarnings({"squid:S1171", "squid:S3599"})
    public static final Set<ResponseCodeEnum> NOT_CHANGE_RESPONSE_STATUS_CODE_SET = Collections.unmodifiableSet(new HashSet<ResponseCodeEnum>() {{
        add(ResponseCodeEnum.SUCCESS);
    }});

    private FeignConstant() {
        // sonar检测
        throw new IllegalStateException("不允许实例化");
    }


}
