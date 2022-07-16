package com.github.codingsoldier.starter.web.filter;

import com.github.codingsoldier.starter.web.properties.RequestLoggingProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(RequestLoggingProperties.class)
public class RequestLoggingFilter extends CommonsRequestLoggingFilter{

    private RequestLoggingProperties properties;

    public RequestLoggingFilter(RequestLoggingProperties properties) {
        super();
        this.properties = properties;
        super.setIncludeClientInfo(properties.isIncludeClientInfo());
        super.setIncludeHeaders(properties.isIncludeHeaders());
        super.setIncludeQueryString(properties.isIncludeQueryString());
        super.setIncludePayload(properties.isIncludePayload());
        super.setMaxPayloadLength(properties.getMaxPayloadLength());
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        // 不打印
    }

    /**
     * afterRequest在ResponseBodyWrapperAdvice#beforeBodyWrite方法后执行，导致先打印responseBody，后打印request信息
     * @param request
     * @param message
     */
    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        if (properties.isRequestResponseBodyLog() || properties.isRequestLog()) {
            String decodeQueryString = "";
            String queryString = request.getQueryString();
            try {
                decodeQueryString = StringUtils.isNotBlank(queryString)
                        ? URLDecoder.decode(queryString, StandardCharsets.UTF_8.name()) : "";
            } catch (UnsupportedEncodingException e) {
                log.error("解码queryString失败", e);
            }
            log.info("在请求完成后才打印Request信息={}，queryString解码=[{}]", message, decodeQueryString);
        }
    }

    // @Override
    // protected void afterRequest(HttpServletRequest request, String message) {
    //     if (properties.isRequestResponseBodyLog() || properties.isRequestLog()) {
    //         String requestURI = request.getRequestURI();
    //         Map<String, String[]> parameterMap = request.getParameterMap();
    //         // 获取body
    //         String[] msgArr = message.split(",");
    //         HashMap<String, Object> bodyMap = new HashMap<>();
    //         if (msgArr != null && msgArr.length > 0) {
    //             for (int i = 0; i < msgArr.length; i++) {
    //                 String[] elemArr = msgArr[i].split("=");
    //                 if (elemArr != null && elemArr.length > 1
    //                         && StringUtils.equals("payload", StringUtils.trim(elemArr[0]))) {
    //                     bodyMap.put(elemArr[0], elemArr[1]);
    //                     break;
    //                 }
    //             }
    //         }
    //         HashMap<String, Object> logData = new HashMap<>();
    //         logData.put("requestURI", requestURI);
    //         logData.put("requestParam", parameterMap.toString());
    //         logData.put("body", bodyMap);
    //         log.info("打印request数据，{}", logData);
    //
    //     }
    // }

}
