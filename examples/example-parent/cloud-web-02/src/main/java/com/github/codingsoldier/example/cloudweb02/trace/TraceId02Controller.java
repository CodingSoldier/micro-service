package com.github.codingsoldier.example.cloudweb02.trace;

import com.github.codingsoldier.common.exception.HttpStatus5xxException;
import com.github.codingsoldier.common.util.TraceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
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
@RequestMapping("/trace")
public class TraceId02Controller {

    @Autowired
    private Web02Service web02Service;
    @Autowired
    private AsyncTaskExecutor microServiceExecutor;

    @GetMapping(value = "/testTraceId", produces = MediaType.APPLICATION_JSON_VALUE)
    public String testTraceId(@RequestParam("name") String name) {
        String traceId = TraceUtil.getMdcTraceId();
        log.info("#####traceId###{}", traceId);
        String mdcReqTrace = web02Service.testTraceId(name);
        return String.format("web02-header-%s-mdc-%s", traceId, mdcReqTrace);
    }

    @GetMapping(value = "/asyncAnno", produces = MediaType.APPLICATION_JSON_VALUE)
    public String asyncAnno(@RequestParam("name") String name) {
        String traceId = TraceUtil.getMdcTraceId();
        web02Service.asyncAnno(name);
        return String.format("web02-header-%s", traceId);
    }

    @GetMapping(value = "/throw/ex", produces = MediaType.APPLICATION_JSON_VALUE)
    public String throwEx(@RequestParam("name") String name) {
        throw new HttpStatus5xxException("测试异常traceid");
    }

    @GetMapping(value = "/thread/ex", produces = MediaType.APPLICATION_JSON_VALUE)
    public String threadEx(@RequestParam("name") String name) {
        microServiceExecutor.execute(() -> {
            log.info("###有traceid--ThreadPoolTraceUtil线程池中打印日志");
            throw new HttpStatus5xxException("WWWWWWWWWWWWWWWW");
        });
        return "web02返回结果";
    }

}
