package com.center.medical.admin.task;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.center.medical.common.thread.CustomThreadPool;
import com.center.medical.common.utils.ip.IpUtils;
import com.center.medical.pacslis.service.PacsInterfaceService;
import com.center.medical.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * pacs相关定时任务
 * @author xhp
 * @since 2025-02-26 10:18
 */
@Slf4j
@Component("pacsTask")
@DisallowConcurrentExecution
public class PacsTask {
    @Resource
    private PacsInterfaceService pacsInterfaceService;
    @Resource
    private ISysConfigService iSysConfigService;
    private final ExecutorService pacsExecutorService = CustomThreadPool.createFixedThreadPool(1, 10);

    /**
     * 获取Pas结果数据
     *
     * @param duringTime 定时任务执行的间隔时间
     */
    public void receiveData(Integer duringTime) {
        log.info("开始获取pacs数据（{}）：PacsTask.receiveData", duringTime);
        Date start = new Date();
        DateTime offset = DateUtil.offset(start, DateField.MINUTE, duringTime);
        List<String> ips = IpUtils.getInnerHostIp();
        if (iSysConfigService.sysJobAuthIp(100L, ips)) {
            log.info("获取pacs数据，进入线程池...");
            pacsExecutorService.submit(() -> {
                log.info("获取pacs数据，线程开始...");
                List<String> patientcodes = pacsInterfaceService.receivePacsDataUser();
                log.info("尚未获取pacs结果数据的体检号：{}", patientcodes);
                for (String item : patientcodes) {
                    Date now = new Date();
                    if (now.after(offset)) {
                        log.info("获取Pacs数据，提前结束，超时了");
                        break;
                    }
                    try {
                        pacsInterfaceService.receive(item);
                    } catch (Exception e) {
                        log.error("获取Pacs数据失败体检号：" + item);
                        log.error(e.getMessage());
                    }
                }
                log.info("获取pacs数据，线程结束...");
            });

        }
    }












}
