package com.github.codingsoldier.starter.web.interceptor;


import com.github.codingsoldier.common.constant.FeignConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


import static com.github.codingsoldier.common.constant.FeignConstant.PROVIDER_FUNTION_RETURN_TYPE;

/**
 * feign拦截器
 * 主要功能是将 feign 请求的 Controller 接口的返回值类型添加到 RequestAttributes 中
 * @author chenpq05
 * @since 2022/2/11 11:58
 */
@Slf4j
public class FeignInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    try {
      String isFeignReqStr = request.getHeader(FeignConstant.IS_FEIGN_REQUEST);
      boolean isFeignReq = StringUtils.equalsIgnoreCase(Boolean.TRUE.toString(), isFeignReqStr);
      if (isFeignReq && (handler instanceof HandlerMethod)) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 将方法返回值类型存储到RequestContextHolder
        String returnTypeName = handlerMethod.getMethod().getReturnType().getName();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
          requestAttributes.setAttribute(PROVIDER_FUNTION_RETURN_TYPE, returnTypeName, 0);
        }
        RequestContextHolder.setRequestAttributes(requestAttributes);
      }
    } catch (Exception e){
      log.error("starter-web 添加 requestAttributes 异常", e);
    }
    return true;
  }
}
