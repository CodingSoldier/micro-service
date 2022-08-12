package com.github.codingsoldier.example.cloudweb01.feign.exception;

import com.github.codingsoldier.common.exception.ResultNotSuccessFeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
public class ExceptionFallbackFactory implements FallbackFactory<Web02FeignExceptionFallbackClient> {

    @Override
    public Web02FeignExceptionFallbackClient create(Throwable cause) {
        /**
         * 服务提供方抛出异常，starter-openfeign 将异常封装为 ResultNotSuccessFeignException，直接抛出异常即可
         */
        if (cause instanceof ResultNotSuccessFeignException) {
            throw (ResultNotSuccessFeignException)cause;
        }
        log.error("Web02FeignExceptionClient.name()调用异常", cause);
        return new Web02FeignExceptionFallbackClient()  {
            @Override
            public Map<String, Object> name(String name) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("key", "服务降级");
                return map;
            }
        };
    }

}
