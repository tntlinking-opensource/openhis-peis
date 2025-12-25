package com.center.medical.center.common.task;

import com.center.medical.center.common.service.RilinSyncResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 对接瑞林萨尔健康管理系统,基础数据同步定时任务
 * @author xhp
 * @since 2025-03-22 8:28
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RilinBasicTask {
    private final RilinSyncResultService rilinSyncResultService;

    /**
     * 实时同步，无并发
     */
    @Scheduled(cron = "${rilin.cron.basic:-}")
    public void sync(){
        rilinSyncResultService.syncBasicIncremental();
    }

    /**
     * 全表同步
     *
     * 基础数据都是从线上同步到线下，然后再从线下上传瑞林服务器
     * 线上服务器时间也可能不一样
     * 为防止漏数据，每天晚上执行一次全表同步
     *
     * 新中心上新系统时，会导入一些基础数据，创建时间、修改时间都是以前的
     */
    @Scheduled(cron = "${rilin.cron.basicFull:-}")
    public void syncFull(){
        rilinSyncResultService.syncBasicFull();
    }
}
