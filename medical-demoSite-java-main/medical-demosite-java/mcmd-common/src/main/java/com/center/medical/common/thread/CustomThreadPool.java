package com.center.medical.common.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class CustomThreadPool {
//    public static ExecutorService createFixedThreadPool(int nThreads, int queueSize) {
//        return new ThreadPoolExecutor(nThreads, nThreads,
//                0L, TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<Runnable>(queueSize));
//    }

    public static ExecutorService createFixedThreadPool(int corePoolSize, int queueCapacity) {
        // 创建一个有界阻塞队列
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(queueCapacity);

        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,  // 核心线程数
                corePoolSize,  // 最大线程数
                0L,            // 空闲线程存活时间
                TimeUnit.MILLISECONDS,
                queue,
                new ThreadPoolExecutor.CallerRunsPolicy() // 使用CallerRunsPolicy作为拒绝策略
        );

        // 设置拒绝策略：当队列满了之后，直接丢弃任务
        executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                // 可以在这里添加日志或者其他处理逻辑
                log.info("线程队列中已满，略过进入队列排队：{}", "Task " + r.toString() + " rejected from " + executor.toString());
            }
        });

        // 预启动所有核心线程
        executor.prestartAllCoreThreads();

        return executor;
    }

}