package com.github.codingsoldier.example.cloudweb02.trace;

import com.github.codingsoldier.common.exception.HttpStatus5xxException;
import com.github.codingsoldier.starter.micrometer.tracing.config.TheadPoolTraceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.github.codingsoldier.common.constant.TraceConstant.X_REQ_TRACE_ID;

@Slf4j
@RefreshScope
@RestController
@RequestMapping("/trace")
public class TraceId02Controller {

    @Autowired
    private Web02Service web02Service;

    @GetMapping(value = "/testTraceId", produces = MediaType.APPLICATION_JSON_VALUE)
    public String testTraceId(@RequestHeader Map<String, String> headers, String name) {
        log.info("#####headers###{}", headers);
        String mdcReqTrace = web02Service.testTraceId(name);
        return String.format("web02-header-%s-mdc-%s", headers.get(X_REQ_TRACE_ID), mdcReqTrace);
    }

    @GetMapping(value = "/asyncAnno", produces = MediaType.APPLICATION_JSON_VALUE)
    public String asyncAnno(@RequestHeader Map<String, String> headers, String name) {
        String mdcReqTrace = web02Service.asyncAnno(name);
        return String.format("web02-header-%s-mdc-%s", headers.get(X_REQ_TRACE_ID), mdcReqTrace);
    }

    @GetMapping(value = "/throw/ex", produces = MediaType.APPLICATION_JSON_VALUE)
    public String throwEx(@RequestHeader Map<String, String> headers, String name) {
        throw new HttpStatus5xxException("测试异常traceid");
    }

    @GetMapping(value = "/thread/ex", produces = MediaType.APPLICATION_JSON_VALUE)
    public String threadEx(String name) {
        TheadPoolTraceUtil.execute(() -> {
            log.info("###有traceid--ThreadPoolTraceUtil线程池中打印日志");
            throw new HttpStatus5xxException("WWWWWWWWWWWWWWWW");
        });
        return "web02返回结果";
    }

}