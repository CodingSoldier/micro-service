package com.github.codingsoldier.example.cloudwebapi.web02;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * cloud-web-02链路追踪测试客户端。
 */
@FeignClient(value = "cloud-web-02", contextId = "web02TraceidClient", path = "/cloud-web-02/trace")
public interface Web02TraceIdClient {

    /**
     * 测试链路追踪ID透传。
     *
     * @param name 姓名
     * @return 测试结果
     */
    @GetMapping(value = "/testTraceId", produces = MediaType.APPLICATION_JSON_VALUE)
    String testTraceId(@RequestParam("name") String name);

    /**
     * 测试异步链路追踪ID透传。
     *
     * @param name 姓名
     * @return 测试结果
     */
    @GetMapping(value = "/asyncAnno", produces = MediaType.APPLICATION_JSON_VALUE)
    String asyncAnno(@RequestParam("name") String name);

    /**
     * 测试异常链路追踪ID。
     *
     * @param name 姓名
     * @return 测试结果
     */
    @GetMapping(value = "/throw/ex", produces = MediaType.APPLICATION_JSON_VALUE)
    String throwEx(@RequestParam("name") String name);

    /**
     * 测试线程池链路追踪ID。
     *
     * @param name 姓名
     * @return 测试结果
     */
    @GetMapping(value = "/thread/ex", produces = MediaType.APPLICATION_JSON_VALUE)
    String threadEx(@RequestParam("name") String name);
}
