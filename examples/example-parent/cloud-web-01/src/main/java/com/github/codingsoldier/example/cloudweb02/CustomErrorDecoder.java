package com.github.codingsoldier.example.cloudweb02;

import com.github.codingsoldier.common.enums.RespCodeEnum;
import com.github.codingsoldier.common.exception.AppException;
import com.github.codingsoldier.common.util.objectmapper.ObjectMapperUtil;
import com.github.codingsoldier.starterweb.resp.Result;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("XXXXXXXXXXXXXX feign client error,response is {}:",response);
        try {
            //获取数据
            String content = ObjectMapperUtil.writeValueAsString(response.body().asInputStream());
            Result result = ObjectMapperUtil.getObjectMapper().readValue(response.body().asInputStream(), Result.class);

            if(result != null && !Objects.equals(result.getCode(), RespCodeEnum.SUCCESS.getCode())){
                return new AppException(result.getCode(), result.getMessage());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new AppException("Feign client 调用异常");
    }
}