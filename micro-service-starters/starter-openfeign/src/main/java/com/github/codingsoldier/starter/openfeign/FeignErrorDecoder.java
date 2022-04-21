package com.github.codingsoldier.starter.openfeign;

import com.github.codingsoldier.common.exception.AppException;
import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.common.util.objectmapper.ObjectMapperUtil;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            // 获取数据
            Result result = ObjectMapperUtil.getObjectMapper().readValue(response.body().asInputStream(), Result.class);
            if (result != null){
                if (response.status() >= 400 && response.status() <= 499) {
                    log.error("feign请求，返回4XX。result={}", result.toString());
                    return new AppException(result.getCode(), result.getMessage());
                } else if (response.status() >= 500 && response.status() <= 599) {
                    log.error("feign请求，返回5XX。result={}", result.toString());
                    return new Exception(result.getMessage());
                }
            }
        } catch (IOException e) {
            return e;
        }
        return new AppException("微服务调用异常。");
    }
}