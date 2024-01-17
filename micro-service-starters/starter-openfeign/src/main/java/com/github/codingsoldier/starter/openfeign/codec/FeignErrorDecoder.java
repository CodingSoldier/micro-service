package com.github.codingsoldier.starter.openfeign.codec;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.codingsoldier.common.exception.feign.FeignResultErrorException;
import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.common.util.objectmapper.ObjectMapperUtil;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;

/**
 * feign调用，http status 不是 200，ErrorDecoder 实现类处理返回结果
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @SuppressWarnings("squid:S1141")
    @Override
    public Exception decode(String methodKey, Response response) {
        try (InputStream inputStream = response.body().asInputStream()){
            String bodyStr = new String(inputStream.readAllBytes(), UTF_8);
            // 获取数据
            Result<?> result;
            try {
                result = ObjectMapperUtil.newObjectMapper()
                    .readValue(bodyStr, Result.class);
            } catch (JsonProcessingException e) {
                log.error("微服务调用，将body转换为Result异常，body为{}", bodyStr, e);
                return new FeignResultErrorException(bodyStr);
            }

            log.warn("feign调用，下游服务未返回成功，response.status() = {}，result={}",
                    response.status(), result);
            if (result != null) {
                return new FeignResultErrorException(result.getCode(), result.getMessage());
            }
        } catch (IOException e) {
            log.error("微服务调用，异常编码实现类发生IOException", e);
            return new FeignResultErrorException(e.getMessage());
        }
        return new FeignResultErrorException("微服务调用异常。");
    }

}