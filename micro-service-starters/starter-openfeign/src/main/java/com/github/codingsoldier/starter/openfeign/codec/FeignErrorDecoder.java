package com.github.codingsoldier.starter.openfeign.codec;

import com.github.codingsoldier.common.exception.feign.FeignResultErrorException;
import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.common.util.objectmapper.ObjectMapperUtil;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * feign调用，http status 不是 200，ErrorDecoder 实现类处理返回结果
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        log.warn("feign调用，下游服务未返回成功，response.status() = {}", response.status());
        try {
            // 获取数据
            Result<?> result = ObjectMapperUtil.newObjectMapper()
                    .readValue(response.body().asInputStream(), Result.class);
            if (result != null) {
                log.warn("feign调用，下游服务未返回成功，http status 不是 200，result={}", result.toString());
                return new FeignResultErrorException(result.getCode(), result.getMessage());
            }
        } catch (IOException e) {
            log.error("微服务调用异常编码实现类发生IOException", e);
            return new FeignResultErrorException(e.getMessage());
        }
        return new FeignResultErrorException("微服务调用异常。");
    }

}