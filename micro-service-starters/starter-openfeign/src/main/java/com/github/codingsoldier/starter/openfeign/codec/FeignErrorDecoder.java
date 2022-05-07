package com.github.codingsoldier.starter.openfeign.codec;

import com.github.codingsoldier.common.exception.AppException;
import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.common.util.objectmapper.ObjectMapperUtil;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * feign调用，http status 不是 200，抛出异常
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            // 获取数据
            Result result = ObjectMapperUtil.getObjectMapper().readValue(response.body().asInputStream(), Result.class);
            if (result != null) {
                int num400 = 400;
                int num499 = 499;
                if (response.status() >= num400 && response.status() <= num499) {
                    log.error("feign请求，返回4XX。result={}", result.toString());
                    return new AppException(result.getCode(), result.getMessage());
                } else {
                    int num500 = 500;
                    int num599 = 599;
                    if (response.status() >= num500 && response.status() <= num599) {
                        log.error("feign请求，返回5XX。result={}", result.toString());
                        return new Exception(result.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            return e;
        }
        return new AppException("微服务调用异常。");
    }
}