package com.github.codingsoldier.example.cloudwebapi.web02;

import com.github.codingsoldier.common.constant.TraceConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cloud-web-02", contextId = "web02TraceidClient", path = "/cloud-web-02/trace")
public interface Web02TraceIdClient {

    @GetMapping(value = "/testTraceId", produces = MediaType.APPLICATION_JSON_VALUE)
    String testTraceId(@RequestHeader(TraceConstant.X_REQ_TRACE_ID) String traceId, @RequestParam("name") String name);

    @GetMapping(value = "/asyncAnno", produces = MediaType.APPLICATION_JSON_VALUE)
    String asyncAnno(@RequestHeader(TraceConstant.X_REQ_TRACE_ID) String traceId, @RequestParam("name") String name);

    @GetMapping(value = "/throw/ex", produces = MediaType.APPLICATION_JSON_VALUE)
    String throwEx(@RequestHeader(TraceConstant.X_REQ_TRACE_ID) String traceId, @RequestParam("name") String name);

    @GetMapping(value = "/thread/ex", produces = MediaType.APPLICATION_JSON_VALUE)
    String threadEx(@RequestHeader(TraceConstant.X_REQ_TRACE_ID) String traceId, @RequestParam("name") String name);
}
