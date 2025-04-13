package com.github.codingsoldier.example.cloudweb01.feign;

import com.github.codingsoldier.common.exception.HttpStatus5xxException;
import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.example.cloudwebapi.web02.Web02FeignExceptionClient;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
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

    /**
     * resilience4j @CircuitBreaker 熔断器测试
     */
    @GetMapping(value = "/circuitBreaker01")
    @CircuitBreaker(name = "cloud-web-02", fallbackMethod = "circuitBreaker01")
    public Map<String, Object> circuitBreaker01(@RequestParam(value = "name") String name) throws Exception {
        Map<String, Object> m = web02FeignExceptionClient.name(name);
        return m;
    }
    /**
     * 有问题，没有返回封装类Result，所以返回给前端的status=200
     * 降级方法中抛出异常反而更合适
     */
    public Map<String, Object> circuitBreaker01(String name, Throwable t) throws Exception {
        log.error("circuitBreaker01，name={}", name, t);
        return Map.of("value", "circuitBreaker01进行降级");
    }

    @GetMapping(value = "/circuitBreaker02Result")
    @CircuitBreaker(name = "cloud-web-02", fallbackMethod = "circuitBreaker02Result")
    public Result<String> circuitBreaker02Result(@RequestParam(value = "name") String name) throws Exception{
        Result<String> result = web02FeignExceptionClient.resultTypeNotChange(name);
        log.info("circuitBreaker02Result，result结果={}", result);
        return result;
    }
    public Result<String> circuitBreaker02Result(String name, Throwable t) throws Exception {
        log.error("circuitBreaker02Result，name={}", name, t);
        return Result.fail("circuitBreaker02Result进行降级");
    }

    @GetMapping(value = "/circuitBreaker03Timeout", produces = MediaType.APPLICATION_JSON_VALUE)
    @CircuitBreaker(name = "cloud-web-02", fallbackMethod = "circuitBreaker03Timeout")
    public String circuitBreaker03Timeout(@RequestParam(value = "timeout") Long timeout) {
        String resp = web02FeignExceptionClient.timeout01(timeout);
        return resp;
    }
    public String circuitBreaker03Timeout(Long timeout, Throwable t) {
        log.error("circuitBreaker03Timeout，timeout={}", timeout, t);
        throw new HttpStatus5xxException("circuitBreaker03Timeout服务降级超时");
    }

    /**
     * resilience4j @Bulkhead 舱壁测试
     */
    @GetMapping(value = "/bulkhead/semaphore/01", produces = MediaType.APPLICATION_JSON_VALUE)
    @Bulkhead(name = "cloud-web-02", fallbackMethod = "bulkheadSemaphore01", type = Bulkhead.Type.SEMAPHORE)
    public String bulkheadSemaphore01(@RequestParam(value = "timeout") Long timeout) {
        String resp = web02FeignExceptionClient.timeout01(timeout);
        return resp;
    }
    public String bulkheadSemaphore01(Long timeout, Throwable t) {
        log.error("bulkheadSemaphore01，timeout={}", timeout, t);
        throw new HttpStatus5xxException("bulkheadSemaphore01人数太多，请稍后重试。");
    }

    /**
     * resilience4j @Bulkhead 舱壁测试
     * 使用THREADPOOL方式，必须返回CompletableFutrue
     */
//    @GetMapping("/bulkhead/threadPool/01")
//    @Bulkhead(name = "cloud-web-02", fallbackMethod = "bulkheadThreadPool01Fallback", type = Bulkhead.Type.THREADPOOL)
//    public CompletableFuture<Result<String>> bulkheadThreadPool01(@RequestParam(value = "timeout") Long timeout) {
//        return CompletableFuture.supplyAsync(() -> {
//            String s = web02FeignExceptionClient.timeout01(timeout) + "Bulkhead.Type.THREADPOOL";
//            return Result.success(s);
//        });
//    }
//
//    public CompletableFuture<Result<String>> bulkheadThreadPool01Fallback(Long timeout, Throwable t) {
//        log.error("bulkheadThreadPool01Fallback，timeout={}", timeout, t);
//        return CompletableFuture.supplyAsync(()-> Result.fail("bulkheadThreadPool01Fallback人数太多，请稍后重试"));
//    }

    /**
     * resilience4j @RateLimiter 限流
     */
    @GetMapping(value = "/bulkhead/rateLimiter/01", produces = MediaType.APPLICATION_JSON_VALUE)
    @RateLimiter(name = "cloud-web-02", fallbackMethod = "bulkheadRateLimiter01Fallback")
    public Result<String> bulkheadRateLimiter01(@RequestParam(value = "name") String name) {
        Result<String> r = web02FeignExceptionClient.resultTypeNotChange(name);
        return r;
    }
    public Result<String> bulkheadRateLimiter01Fallback(String name, Throwable t) {
        log.error("bulkheadRateLimiter01Fallback，name={}", name, t);
        throw new HttpStatus5xxException("bulkheadRateLimiter01Fallback人数太多，已经限流。");
    }

}