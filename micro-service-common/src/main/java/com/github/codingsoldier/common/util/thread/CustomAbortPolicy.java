package com.github.codingsoldier.common.util.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义程池饱和策略，模仿AbortPolicy（终止）
 * <p>
 * A handler for rejected tasks that throws a
 * {@code RejectedExecutionException}.
 */
@Slf4j
public class CustomAbortPolicy implements RejectedExecutionHandler {

    /**
     * Creates an {@code AbortPolicy}.
     */
    public CustomAbortPolicy() {
        // sonar检测
    }

    /**
     * Always throws RejectedExecutionException.
     *
     * @param r the runnable task requested to be executed
     * @param e the executor attempting to execute this task
     * @throws RejectedExecutionException always
     */
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        String msg = String.format("线程池满了，抛出异常。%n Task: %s 。%n Rejected from: %s",
                r.toString(), e.toString());
        log.info(msg);
        throw new RejectedExecutionException(msg);
    }

}
