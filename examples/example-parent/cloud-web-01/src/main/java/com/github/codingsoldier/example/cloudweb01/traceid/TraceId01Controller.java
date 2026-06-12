package com.github.codingsoldier.example.cloudweb01.traceid;

import com.github.codingsoldier.common.util.TraceUtil;
import com.github.codingsoldier.example.cloudwebapi.web02.Web02TraceIdClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 加上请求头 x-req-trace-id
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("/trace")
public class TraceId01Controller {

    @Autowired
    private Web02TraceIdClient web02TraceidClient;

    @GetMapping(value = "/testTraceId", produces = MediaType.APPLICATION_JSON_VALUE)
    public String testTraceId(@RequestParam("name") String name) {
        String traceId = TraceUtil.getMdcTraceId();
        String resp = web02TraceidClient.testTraceId(name);
        String mdcReqTrace = TraceUtil.getMdcTraceId();
        return String.format("web01-header-%s-mdc-%s-%s", traceId, mdcReqTrace, resp);
    }

    @GetMapping(value = "/asyncAnno", produces = MediaType.APPLICATION_JSON_VALUE)
    public String asyncAnno(@RequestParam("name") String name) {
        String traceId = TraceUtil.getMdcTraceId();
        String resp = web02TraceidClient.asyncAnno(name);
        String mdcReqTrace = TraceUtil.getMdcTraceId();
        return String.format("web01-header-%s-mdc-%s-%s", traceId, mdcReqTrace, resp);
    }

    @GetMapping(value = "/throw/ex", produces = MediaType.APPLICATION_JSON_VALUE)
    public String throwEx(@RequestParam("name")  String name) {
        web02TraceidClient.throwEx(name);
        return "aaa";
    }

    @GetMapping(value = "/get/mcd/req", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMcdReq(@RequestParam(value = "name", required = false) String name) {
        String id = TraceUtil.getMdcTraceId();
        log.info("###x-req-trace-id###{}", id);
        return id;
    }

    @GetMapping(value = "/thread/ex", produces = MediaType.APPLICATION_JSON_VALUE)
    public String threadEx(@RequestParam("name") String name) {
        web02TraceidClient.threadEx(name);
        return web02TraceidClient.threadEx(name);
    }

}
