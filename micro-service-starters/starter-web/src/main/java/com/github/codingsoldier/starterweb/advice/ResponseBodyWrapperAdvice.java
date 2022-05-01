package com.github.codingsoldier.starterweb.advice;

import com.github.codingsoldier.common.util.objectmapper.ObjectMapperUtil;
import com.github.codingsoldier.starterweb.annotation.NoWrapper;
import com.github.codingsoldier.starterweb.constant.FeignConstant;
import com.github.codingsoldier.common.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

/**
 * 将controller返回值包装为Result对象
 * @RestControllerAdvice 加上路径，避免对其他包进行包装
 */
@Slf4j
@RestControllerAdvice
public class ResponseBodyWrapperAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 类上使用了 @NoWrapper
        if (returnType.getDeclaringClass().isAnnotationPresent(NoWrapper.class)){
            return false;
        }
        // 方法上使用了 @NoWrapper
        if (returnType.getMethod().isAnnotationPresent(NoWrapper.class)){
            return false;
        }

        // springfox 接口不包装返回值
        if (returnType.getDeclaringClass().getName().contains("springfox")){
            return false;
        }

        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {


        // 如果是Feign请求则直接返回实体对象
        List<String> valList = request.getHeaders().get(FeignConstant.FEIGN_REQUEST);
        if (CollectionUtils.isNotEmpty(valList)){
            boolean isFeignRequest = valList.contains(Boolean.TRUE.toString());
            if(isFeignRequest){
                log.debug("feign请求，不对返回结果进行包装。");
                return body;
            }
        }


        if (body instanceof Result) {
            return body;
        }

        if (body instanceof String || body == null) {
            // 因为StringHttpMessageConverter会直接把字符串写入body, 所以字符串特殊处理
            // body == null ，返回值将被StringHttpMessageConverter处理
            return ObjectMapperUtil.writeValueAsString(Result.success(body));
        }

        // 其他类型进行统一包装
        return Result.success(body);
    }
}
