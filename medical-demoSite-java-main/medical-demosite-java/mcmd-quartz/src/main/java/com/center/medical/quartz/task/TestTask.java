package com.center.medical.quartz.task;

import org.quartz.DisallowConcurrentExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 测试定时任务
 */
@Component("testTask")
@DisallowConcurrentExecution
public class TestTask {

    private static final Logger log = LoggerFactory.getLogger(TestTask.class);

    /**
     * 无参定时任务
     */
    public void noParams() {
        log.info("执行无参定时任务TestTask.noParams");
    }

    /**
     * 有参定时任务
     *
     * @param l
     */
    public void hadParams(Long l) {
        log.info("执行有参定时任务TestTask.hadParams:{}", l);
        try {
            Thread.sleep(l);             // 等待指定的时间
            log.info("执行有参定时任务TestTask.hadParams,结束休眠");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 多参定时任务
     *
     * @param str
     * @param i
     * @param l
     */
    public void multParams(String str, Integer i, Long l) {
        log.info("执行多参定时任务TestTask.multParams:str={}, i={}, l={}", str, i, l);
    }
}
