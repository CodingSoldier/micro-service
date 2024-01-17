package com.github.codingsoldier.starter.web.advice;

import com.github.codingsoldier.common.constant.FeignConstant;
import com.github.codingsoldier.common.constant.OrderConstant;
import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.common.util.objectmapper.ObjectMapperUtil;
import com.github.codingsoldier.starter.web.annotation.NoWrapper;
import com.github.codingsoldier.starter.web.properties.LoggingProperties;
import java.lang.reflect.Method;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 将controller返回值包装为Result对象，以及Response日志处理
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Slf4j
@Order(OrderConstant.ADVICE_WRAPPER)
@RestControllerAdvice
public class ResponseBodyWrapperAdvice implements ResponseBodyAdvice<Object> {

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    private final LoggingProperties properties;

    public ResponseBodyWrapperAdvice(LoggingProperties properties) {
        this.properties = properties;
    }

    @Override
    public boolean supports(MethodParameter returnType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        // 类上使用了 @NoWrapper
        if (returnType.getDeclaringClass().isAnnotationPresent(NoWrapper.class)) {
            return false;
        }
        // 方法上使用了 @NoWrapper
        Method method = returnType.getMethod();
        if (method == null || method.isAnnotationPresent(NoWrapper.class)) {
            return false;
        }

        // springfox、springdoc 接口不包装返回值
        String name = returnType.getDeclaringClass().getName();
        return !StringUtils.isNotBlank(name)
                || !StringUtils.containsAny(name, "springfox", "springdoc");
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {
        if (body == null) {
            return null;
        }

        String actuatorPrefix = contextPath + "/actuator";
        String uriPath = request.getURI().getPath();
        if (uriPath.startsWith(actuatorPrefix)) {
            return body;
        }

        // 封装为一个方法仅是为了方便打印返回结果
        Object bodyObj = handleBody(body, returnType, selectedConverterType, request, response);

        // 打印responseBody
        try {
            if (properties.isRequestResponseLog() || properties.isResponseBodyLog()) {
                String bodyStr = ObjectMapperUtil.writeValueAsString(bodyObj);
                log.info("打印ResponseBody信息 = {}", bodyStr);
            }
        }catch (Exception e) {
            log.error("打印ResponseBody时，body转string异常", e);
        }

        return bodyObj;
    }

    private Object handleBody(
            Object body,
            MethodParameter returnType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {

        // 如果是Feign请求不包装返回结果
        List<String> valList = request.getHeaders().get(FeignConstant.IS_FEIGN_REQUEST);
        boolean isFeignRequest = CollectionUtils.isNotEmpty(valList)
                && valList.contains(Boolean.TRUE.toString());
        if (isFeignRequest) {
            log.debug("feign请求，不对返回结果进行包装。path={}", request.getURI().getPath());
            return body;
        } else if (body instanceof Result) {
            return body;
        } else if (selectedConverterType != null
                && StringHttpMessageConverter.class.isAssignableFrom(selectedConverterType)) {
            // 返回值将被StringHttpMessageConverter处理，必须把Result转为String
            return ObjectMapperUtil.writeValueAsString(Result.success(body));
        }

        // 其他类型进行统一包装
        return Result.success(body);
    }


}
