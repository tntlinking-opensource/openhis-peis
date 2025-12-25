package com.center.medical.admin.task;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.thread.CustomThreadPool;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ip.IpUtils;
import com.center.medical.common.utils.redis.RedisUtil;
import com.center.medical.pacslis.service.LisInterfaceService;
import com.center.medical.pacslis.service.MiddleDbInterfaceService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author: 路飞
 * @date: 2023-02-07 10:46
 * @description: Lis相关的定时任务
 */
@Slf4j
@Component("lisTask")
@DisallowConcurrentExecution
public class LisTask {

    @Resource
    private LisInterfaceService lisInterfaceService;
    @Resource
    private PeispatientfeeitemService peispatientfeeitemService;
    @Resource
    private MiddleDbInterfaceService middleDbInterfaceService;
    @Resource
    private ISysConfigService iSysConfigService;
    private final ExecutorService lisExecutorService = CustomThreadPool.createFixedThreadPool(1, 10);

    /**
     * 获取Lis结果数据
     *
     * @param duringTime 定时任务执行的间隔时间
     */
    public void receiveData(Integer duringTime) {
        log.info("开始获取Lis数据（{}）：lisTask.receiveData", duringTime);
        Date start = new Date();
        DateTime offset = DateUtil.offset(start, DateField.MINUTE, duringTime);
        List<String> ips = IpUtils.getInnerHostIp();
        if (iSysConfigService.sysJobAuthIp(10L, ips)) {
            log.info("获取lis数据，进入线程池...");
            lisExecutorService.submit(() -> {
                log.info("获取lis数据，线程开始...");
                List<String> patientcodes = peispatientfeeitemService.receiveLisDataUser();
                log.info("尚未获取Lis结果数据的体检号：{}", patientcodes);
                for (String item : patientcodes) {
                    Date now = new Date();
                    if (now.after(offset)) {
                        log.info("获取Lis数据，提前结束，超时了");
                        break;
                    }
                    try {
                        lisInterfaceService.receive(item);
                    } catch (Exception e) {
                        log.error("获取Lis数据失败体检号：" + item);
                        log.error(e.getMessage());
                    }
                }
                log.info("获取lis数据，线程结束...");
            });

        }
    }


    /**
     * 数据重发（将插入中间库失败的进行数据重发）
     *
     * @return
     */
    public void resendMiddleDb(Integer duringTime) {
        log.info("数据重发（将插入中间库失败的进行数据重发:{}）lisTask.resendMiddleDb：开始", duringTime);
        Date start = new Date();
        DateTime offset = DateUtil.offset(start, DateField.MINUTE, duringTime);
        List<String> ips = IpUtils.getInnerHostIp();
        if (iSysConfigService.sysJobAuthIp(21L, ips)) {
            //获取插入中间库失败的记录
            Collection<String> keys = RedisUtil.keys(CacheConstants.SEND_MIDDLE_DB_FAIL_KEY + "*");
            // log.info("插入中间库失败的记录keys：{}", JSONUtil.toJsonStr(keys));
            if (keys.size() > 0) {
                for (String key : keys) {
                    Date now = new Date();
                    if (now.after(offset)) {
                        log.info("数据重发（将插入中间库失败的进行数据重发）：提前结束，超时了");
                        break;
                    }
                    String patientcode = RedisUtil.get(key);
                    if (StringUtils.isNotBlank(patientcode)) {
                        middleDbInterfaceService.insert(patientcode);
                    }

                }
            }
            log.info("数据重发（将插入中间库失败的进行数据重发）：结束，执行了{}条记录" + keys);
        }
    }

}
