package com.github.codingsoldier.example.cloudweb01.traceid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cloud-web-02", contextId = "web02TraceidClient", path = "/cloud-web-02/trace")
public interface Web02TraceidClient {

    @GetMapping(value = "/testTraceId", produces = MediaType.APPLICATION_JSON_VALUE)
    String testTraceId(@RequestParam("name") String name);

    @GetMapping(value = "/asyncAnno", produces = MediaType.APPLICATION_JSON_VALUE)
    String asyncAnno(@RequestParam("name") String name);

}
