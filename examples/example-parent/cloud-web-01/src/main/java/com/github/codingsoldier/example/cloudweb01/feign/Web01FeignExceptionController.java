package com.github.codingsoldier.example.cloudweb01.feign;

import com.github.codingsoldier.common.exception.HttpStatus5xxException;
import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.example.cloudwebapi.web02.Web02FeignExceptionClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RefreshScope
@RestController
@RequestMapping("/feign01/exception")
public class Web01FeignExceptionController {

    @Autowired
    private Web02FeignExceptionClient web02FeignExceptionClient;

    @GetMapping(value = "/name")
    @CircuitBreaker(name = "cloud-web-02", fallbackMethod = "name")
    public Map<String, Object> name(@RequestParam(value = "name") String name) throws Exception {
        Map<String, Object> m = web02FeignExceptionClient.name(name);
        return m;
    }

    /**
     * 有问题，没有返回封装类Result，所以返回给前端的status=200
     * 降级方法中抛出异常反而更合适
     */
    public Map<String, Object> name(String name, Throwable t) throws Exception {
        log.error("web02FeignExceptionFallbackClient，name={}", name, t);
        return Map.of("value", "name进行降级");
    }


    @GetMapping(value = "/result/type/not/change")
    @CircuitBreaker(name = "cloud-web-02", fallbackMethod = "resultTypeNotChange")
    public Result<String> resultTypeNotChange(@RequestParam(value = "name") String name) throws Exception{
        Result<String> result = web02FeignExceptionClient.resultTypeNotChange(name);
        log.info("web02FeignExceptionClient，result结果={}", result);
        return result;
    }
    public Result<String> resultTypeNotChange(String name, Throwable t) throws Exception {
        log.error("web02FeignExceptionFallbackClient，name={}", name, t);
        return Result.fail("resultTypeNotChange进行降级");
    }


    @GetMapping(value = "/timeout01", produces = MediaType.APPLICATION_JSON_VALUE)
    @CircuitBreaker(name = "cloud-web-02", fallbackMethod = "timeout01")
    public String timeout01(@RequestParam(value = "timeout") Long timeout) {
        String resp = web02FeignExceptionClient.timeout01(timeout);
        return resp;
    }
    public String timeout01(Long timeout, Throwable t) {
        log.error("web02FeignExceptionFallbackClient，timeout={}", timeout, t);
        throw new HttpStatus5xxException("timeout01服务降级超时");
    }

}