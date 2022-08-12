package com.github.codingsoldier.starter.openfeign.codec;

import com.github.codingsoldier.common.exception.ResultNotSuccessFeignException;
import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.common.util.objectmapper.ObjectMapperUtil;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * feign调用，http status 不是 200，抛出异常
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("feign调用异常，response.status() = {}", response.status());
        try {
            // 获取数据
            Result<?> result = ObjectMapperUtil.newObjectMapper()
                    .readValue(response.body().asInputStream(), Result.class);
            if (result != null) {
                log.error("feign请求，http status 不是 200，result={}", result.toString());
                return new ResultNotSuccessFeignException(result.getCode(), result.getMessage());
            }
        } catch (IOException e) {
            log.error("微服务调用异常编码实现类发生IOException", e);
            return new ResultNotSuccessFeignException(e.getMessage());
        }
        return new ResultNotSuccessFeignException("微服务调用异常。");
    }

}