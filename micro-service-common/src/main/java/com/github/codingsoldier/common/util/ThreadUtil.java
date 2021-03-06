package com.github.codingsoldier.common.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;


/**
 * 线程池工具类
 *
 * @author chenpq05
 * @since 2022-02-09
 */
@Slf4j
public class ThreadUtil {

    private static ThreadPoolExecutor executor = null;

    static {
        initExecutor();
    }

    private ThreadUtil() {
        // sonar检测
        throw new IllegalStateException("不允许实例化");
    }

    public static void main(String[] args) {
        int max = Math.max(1, 5);
        log.info("{}", max);
        log.info("{}", Math.max(5, 3));
    }

    /**
     * 初始化
     */
    private static void initExecutor() {
        int cpuCores = Runtime.getRuntime().availableProcessors();
        cpuCores = Math.max(cpuCores, 5);

        int temp = cpuCores + 2;
        int maxPool = ioMaximumPoolSize();
        maxPool = Math.max(maxPool, temp);

        log.info("初始化线程池 corePoolSize:{} , maximumPoolSize:{}", cpuCores, maxPool);

        // 加上这个纯属为了解决Alibaba代码插件检测
        ThreadFactory defaultThreadFactory = Executors.defaultThreadFactory();
        LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<>(100000);
        executor = new ThreadPoolExecutor(cpuCores, maxPool,
                60L, TimeUnit.SECONDS,
                linkedBlockingQueue,
                defaultThreadFactory,
                new CustomAbortPolicy());
    }

    /**
     * IO密集型应用的最大线程数
     * 公式： CPU核心数 * (1 + 线程平均等待时间 / 线程平均工作时间)
     * <p>
     * 阻塞系数 = 阻塞时间 /（阻塞时间+计算时间）
     * core / （1-阻塞系数） 和 core * ( 1 + w/c ) 这俩是同一公式
     */
    private static int ioMaximumPoolSize() {
        int jvmAvailableProcessors = Runtime.getRuntime().availableProcessors();
        int blockTime = 5;
        int runTime = 1;
        return jvmAvailableProcessors * (1 + blockTime / runTime);
    }

    /**
     * 返回 executor
     *
     * @return
     */
    public static ThreadPoolExecutor getExecutor() {
        return executor;
    }

    /**
     * 在公共线程池中执行任务
     *
     * @param runnable 可运行对象
     */
    public static void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    /**
     * 提交任务
     *
     * @param task
     * @param <T>
     * @return
     */
    public <T> Future<T> submit(Callable<T> task) {
        return executor.submit(task);
    }

    /**
     * 自定义程池饱和策略，模仿AbortPolicy（终止）。发送钉钉消息
     * <p>
     * A handler for rejected tasks that throws a
     * {@code RejectedExecutionException}.
     */
    public static class CustomAbortPolicy implements RejectedExecutionHandler {
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

}
