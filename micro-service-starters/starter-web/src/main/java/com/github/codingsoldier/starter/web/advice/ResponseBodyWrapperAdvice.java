package com.github.codingsoldier.starter.web.advice;

import com.github.codingsoldier.common.enums.ResponseCodeEnum;
import com.github.codingsoldier.common.feign.FeignConstant;
import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.common.util.objectmapper.ObjectMapperUtil;
import com.github.codingsoldier.starter.web.annotation.NoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 将controller返回值包装为Result对象
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class ResponseBodyWrapperAdvice implements ResponseBodyAdvice<Object> {

    @SuppressWarnings("squid:S1126")
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 类上使用了 @NoWrapper
        if (returnType.getDeclaringClass().isAnnotationPresent(NoWrapper.class)) {
            return false;
        }
        // 方法上使用了 @NoWrapper
        Method method = returnType.getMethod();

        if (method == null || method.isAnnotationPresent(NoWrapper.class)) {
            return false;
        }

        // springfox 接口不包装返回值
        String springfox = "springfox";
        String name = returnType.getDeclaringClass().getName();
        if (StringUtils.isNotBlank(name) && name.contains(springfox)) {
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


        // 如果是Feign请求不包装返回结果
        List<String> valList = request.getHeaders().get(FeignConstant.FEIGN_REQUEST);
        boolean isFeignRequest = CollectionUtils.isNotEmpty(valList)
                && valList.contains(Boolean.TRUE.toString());
        if (isFeignRequest) {
            if (body instanceof Result) {
                Result<?> result = (Result) body;
                Set<Integer> notChangeCodes = FeignConstant.NOT_CHANGE_RESPONSE_STATUS_CODE_SET
                        .stream()
                        .map(ResponseCodeEnum::getCode)
                        .collect(Collectors.toSet());
                if (!notChangeCodes.contains(result.getCode())) {
                    log.error("feign调用，返回结果code不是成功ResponseCodeEnum.SUCCESS.getCode()，" +
                            "设置Response.StatusCode=HttpStatus.INTERNAL_SERVER_ERROR");

                    response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            log.debug("feign请求，不对返回结果进行包装。");
            return body;
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
