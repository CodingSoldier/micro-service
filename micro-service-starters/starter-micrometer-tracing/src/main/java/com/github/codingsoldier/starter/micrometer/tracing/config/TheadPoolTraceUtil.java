package com.github.codingsoldier.starter.micrometer.tracing.config;

import com.github.codingsoldier.common.util.thread.CustomAbortPolicy;
import com.github.codingsoldier.common.util.thread.CustomThreadFactory;
import io.micrometer.context.ContextExecutorService;
import io.micrometer.context.ContextSnapshot;

import java.util.concurrent.*;

/**
 * 带 traceId 的线程池工具类
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */

public class TheadPoolTraceUtil {

    private static final ThreadPoolExecutor taskExecutor;

    private TheadPoolTraceUtil() {
        // sonar
    }

    static {
        int cpuCores = Runtime.getRuntime().availableProcessors();
        cpuCores = Math.max(cpuCores, 5);
        int maxPool = cpuCores * 10;

        ThreadFactory customThreadFactory = new CustomThreadFactory("TheadPoolTraceUtil-");

        LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<>(100000);
        taskExecutor = new ThreadPoolExecutor(cpuCores, maxPool,
                60L, TimeUnit.SECONDS,
                linkedBlockingQueue,
                customThreadFactory,
                new CustomAbortPolicy());
    }

    private static ExecutorService getAsyncExecutor() {
        return ContextExecutorService.wrap(taskExecutor, ContextSnapshot::captureAll);
    }

    public static void execute(Runnable runnable) {
        getAsyncExecutor().execute(runnable);
    }

    public static <T> Future<T> submit(Callable<T> task) {
        return getAsyncExecutor().submit(task);
    }

}