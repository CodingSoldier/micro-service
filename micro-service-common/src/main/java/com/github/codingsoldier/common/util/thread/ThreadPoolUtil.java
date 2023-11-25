package com.github.codingsoldier.common.util.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;


/**
 * 线程池工具类
 *
 * @author chenpq05
 * @since 2022-02-09
 */
@Slf4j
public class ThreadPoolUtil {

    private static ThreadPoolExecutor executor = null;

    static {
        initExecutor();
    }

    private ThreadPoolUtil() {
        // sonar检测
        throw new IllegalStateException("不允许实例化");
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

        ThreadFactory customThreadFactory = new CustomThreadFactory("ThreadPoolUtil-");

        LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<>(5000000);
        executor = new ThreadPoolExecutor(cpuCores, maxPool,
                60L, TimeUnit.SECONDS,
                linkedBlockingQueue,
                customThreadFactory,
                new CustomAbortPolicy());
    }

    /**
     * IO密集型应用的最大线程数
     * 公式： CPU核心数 * (1 + 线程平均等待时间 / 线程平均工作时间)
     * <p>
     * 阻塞系数 = 阻塞时间 /（阻塞时间+计算时间）
     * core / （1-阻塞系数） 和 core * ( 1 + w/c ) 这俩是同一公式
     */
    public static int ioMaximumPoolSize() {
        int jvmAvailableProcessors = Runtime.getRuntime().availableProcessors();
        int blockTime = 5;
        int runTime = 1;
        return jvmAvailableProcessors * (1 + blockTime / runTime);
    }

    /**
     * 返回 executor
     *
     * @return ThreadPoolExecutor
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
     * @param task task
     * @return Future
     */
    public static <T> Future<T> submit(Callable<T> task) {
        return executor.submit(task);
    }

}
