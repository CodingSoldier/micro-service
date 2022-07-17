package com.github.codingsoldier.example.gateway.util;

import com.github.codingsoldier.example.gateway.context.ApplicationContextHolder;
import org.springframework.cloud.sleuth.CurrentTraceContext;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.instrument.web.WebFluxSleuthOperators;
import org.springframework.web.server.ServerWebExchange;


public class SleuthLogUtil {

    private SleuthLogUtil() {
        // sonar检测
        throw new IllegalStateException("sonar检测，不允许实例化");
    }

    /**
     * sleuth打印日志，非常消耗性能
     * @param exchange
     * @param runnable
     */
    public static void log(ServerWebExchange exchange, Runnable runnable) {
        Tracer tracer = ApplicationContextHolder.getBean(Tracer.class);
        CurrentTraceContext currentTraceContext = ApplicationContextHolder.getBean(CurrentTraceContext.class);
        // 打印日志
        WebFluxSleuthOperators.withSpanInScope(tracer, currentTraceContext, exchange, runnable);
    }

}
