package com.github.codingsoldier.starter.web.controller;

import com.github.codingsoldier.starter.web.annotation.NoWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 覆盖 http 404 处理逻辑
 * @author chenpq05
 * @since 2023/11/23 15:41
 */

@ConditionalOnProperty(value = "micro-service.starter.web.basic-error-enabled", matchIfMissing = true)
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomBasicErrorController extends BasicErrorController {

  /**
   * import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
   */
  public CustomBasicErrorController(ServerProperties serverProperties) {
    super(new DefaultErrorAttributes(), serverProperties.getError());
  }

  /**
   * 资源没找到，返回404
   */
  @NoWrapper
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  @Override
  public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
    Map<String, Object> map = new HashMap<>();
    HttpStatus status = getStatus(request);
    map.put("code", status.value());
    map.put("data", null);
    map.put("message", status.toString());
    return new ResponseEntity<>(map, status);
  }

}
