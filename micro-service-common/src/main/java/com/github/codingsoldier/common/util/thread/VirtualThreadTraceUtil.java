package com.github.codingsoldier.common.util.thread;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import org.slf4j.MDC;

/**
 * 支持MDC链路追踪上下文传递的虚拟线程工具类。
 *
 * @author chenpq05
 * @since 2026-06-12
 */
public class VirtualThreadTraceUtil {

  private VirtualThreadTraceUtil() {
    // sonar检测
    throw new IllegalStateException("不允许实例化");
  }

  /**
   * 启动携带当前MDC上下文的虚拟线程。
   *
   * @param task 线程任务
   * @return 已启动的虚拟线程
   */
  public static Thread startVirtualThread(Runnable task) {
    Objects.requireNonNull(task, "task不能为空");
    Map<String, String> contextMap = MDC.getCopyOfContextMap();
    return Thread.startVirtualThread(() -> runWithMdc(contextMap, task));
  }

  /**
   * 启动携带当前MDC上下文的虚拟线程，并返回任务结果。
   *
   * @param task 线程任务
   * @param <T> 返回值类型
   * @return 可获取任务结果的Future
   */
  public static <T> Future<T> startVirtualThread(Callable<T> task) {
    Objects.requireNonNull(task, "task不能为空");
    Map<String, String> contextMap = MDC.getCopyOfContextMap();
    CompletableFuture<T> future = new CompletableFuture<>();
    Thread.startVirtualThread(() -> {
      try {
        future.complete(callWithMdc(contextMap, task));
      } catch (Throwable ex) {
        if (ex instanceof InterruptedException) {
          Thread.currentThread().interrupt();
        }
        future.completeExceptionally(ex);
      }
    });
    return future;
  }

  private static void runWithMdc(Map<String, String> contextMap, Runnable task) {
    Map<String, String> previousContextMap = MDC.getCopyOfContextMap();
    setContextMap(contextMap);
    try {
      task.run();
    } finally {
      setContextMap(previousContextMap);
    }
  }

  private static <T> T callWithMdc(Map<String, String> contextMap, Callable<T> task) throws Exception {
    Map<String, String> previousContextMap = MDC.getCopyOfContextMap();
    setContextMap(contextMap);
    try {
      return task.call();
    } finally {
      setContextMap(previousContextMap);
    }
  }

  private static void setContextMap(Map<String, String> contextMap) {
    if (contextMap == null) {
      MDC.clear();
    } else {
      MDC.setContextMap(contextMap);
    }
  }

}
