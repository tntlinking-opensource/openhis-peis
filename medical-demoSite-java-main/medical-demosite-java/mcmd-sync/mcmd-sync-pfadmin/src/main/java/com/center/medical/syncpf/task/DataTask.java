package com.center.medical.syncpf.task;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.utils.ip.IpUtils;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.common.utils.redis.RedisUtil;
import com.center.medical.sync.service.SyncOnlineService;
import com.center.medical.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author: 路飞
 * @date: 2023-02-07 10:46
 * @description: 基础数据同步定时任务
 */
@Slf4j
@Component("syncDataTask")
@DisallowConcurrentExecution
public class DataTask {

    @Resource
    private SyncOnlineService syncOnlineService;
    @Resource
    private ISysConfigService iSysConfigService;

    public static void main(String[] args) {
//        System.out.println("XXX.XXX.XXX.XXX".contains(IpUtils.getHostIp()));
    }

    /**
     * 将缓存中的SyncData同步至数据库中
     *
     * @return
     */
    public void syncDataToDB(Long endNum, Integer duringTime) {
        //log.info("syncDataToDB定时任务开始执行:endNum={}", endNum);
        Date start = new Date();
        DateTime offset = DateUtil.offset(start, DateField.SECOND, duringTime);

        //判断是否允许IP执行
        List<String> ips = IpUtils.getInnerHostIp();
        String externalIp = IpUtils.getExternalIp();
        ips.add(externalIp);
        if (iSysConfigService.sysJobAuthIp(4L, ips)) {
            Date startTime = new Date();
            if (Objects.isNull(endNum) || endNum == 0) {
                endNum = 99L;
            }
            log.info("start:将缓存中的SyncData同步至数据库中定时任务：syncDataTask.syncDataToDB");
            int totalSize = 0;
            //循环执行10次上传操作
            do {
                Date now1 = new Date();
                if (now1.after(offset)) {
                    log.info("将缓存中的SyncData同步至数据库中定时任务，提前结束，超时了");
                    break;
                }
                Set<Object> set = RedisSetUtil.queryByRange(CacheConstants.SYNC_DATA_OBJECTS, null, endNum);
                // log.info("数据同步内容：{}、{}", set, set.size() > 0);
                if (set.size() == 0) {
                    break;
                }
                totalSize = totalSize + set.size();
                syncOnlineService.getSyncContent(set);
//                try {
//                    Thread.sleep(50);             // 等待指定的时间
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            } while (1 == 1);
            Object count = RedisUtil.get("syncDataToDB:" + DateUtil.format(new Date(), "yyyy-MM-dd"));
            Integer todayTotal = 0;
            if (Objects.nonNull(count)) {
                todayTotal = (Integer) count + totalSize;
            } else {
                todayTotal = totalSize;
            }
            RedisUtil.set("syncDataToDB:" + DateUtil.format(new Date(), "yyyy-MM-dd"), todayTotal, 0);

            Date endTime = new Date();
            log.info("end:将SyncDataObjects缓存中的SyncData执行同步{}-{}：本次共执行了{}条，今天共执行了{}条,用时{}S\n", DateUtil.format(startTime, "yyyy-MM-dd HH:mm:ss"), DateUtil.format(endTime, "yyyy-MM-dd HH:mm:ss"), totalSize, todayTotal, DateUtil.between(startTime, endTime, DateUnit.SECOND));
        }

    }

}
