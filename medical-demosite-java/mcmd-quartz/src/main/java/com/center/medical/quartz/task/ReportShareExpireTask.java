package com.center.medical.quartz.task;

import com.center.medical.report.service.ReportShareMainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 报告分享链接过期 定时任务
 */
@Component("reportShareExpireTask")
public class ReportShareExpireTask {

    private static final Logger log = LoggerFactory.getLogger(ReportShareExpireTask.class);

    @Resource
    private ReportShareMainService reportShareMainService;


    /**
     * 比较时间,过期自动修改状态
     */
    public void run() {
        log.info("报告分享链接过期定时任务执行中--------------");
        reportShareMainService.reportShareMainExpire();
    }
}
