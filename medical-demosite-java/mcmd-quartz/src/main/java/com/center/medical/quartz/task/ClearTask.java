package com.center.medical.quartz.task;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.core.domain.BaseEntity;
import com.center.medical.common.core.domain.entity.SysOperLog;
import com.center.medical.common.utils.DateUtils;
import com.center.medical.common.utils.ip.IpUtils;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.quartz.domain.SysJobLog;
import com.center.medical.quartz.service.ISysJobLogService;
import com.center.medical.sync.bean.model.SyncData;
import com.center.medical.system.service.ISysConfigService;
import com.center.medical.system.service.ISysOperLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 清除工具定时任务
 */

@Slf4j
@Component("clearTask")
public class ClearTask {

    @Resource
    private ISysOperLogService sysOperLogService;
    @Resource
    private ISysJobLogService iSysJobLogService;
    @Resource
    private ISysConfigService iSysConfigService;

    /**
     * 系统操作日志缓存到本地文件
     * 将dayNum天前的查出来保存在一个文件里，然后删掉dayNum天前的数据，
     *
     * @param dayNum 多少天前
     */
    public void sysOperLog(Integer dayNum) {
        log.info("---------系统操作日志缓存到本地文件定时任务开始执行---------");

        List<String> ips = IpUtils.getInnerHostIp();
        if (iSysConfigService.sysJobAuthIp(19L, ips)) {
            //获取当前时间的0点
            DateTime dateTime = DateUtil.beginOfDay(new Date());
            DateTime endDate = DateUtil.offsetDay(dateTime, -dayNum);
            List<SysOperLog> sysOperLogs = sysOperLogService.getLeOperTime(endDate);
            String root = System.getProperty("user.dir");
            if (sysOperLogs.size() > 0) {
                Map<Date, List<SysOperLog>> collect = sysOperLogs.stream().collect(Collectors.groupingBy(SysOperLog::getOperTime));
                Set<Date> keys = collect.keySet();
                if (keys.size() == 0) {
                    String day = DateUtils.parseDateToStr("yyyyMMdd", new Date());
                    ExcelUtil<SysOperLog> util = new ExcelUtil<SysOperLog>(SysOperLog.class);
                    util.exportExcelPath(root + "/" + CacheConstants.LOCAL_CACHING_PATH + CacheConstants.LOCAL_SYS_OPER_LOG_CACHING_PATH + day + ".xlsx", sysOperLogs, "系统操作日志");
                    //删除过期数据
                    Long[] longs = sysOperLogs.stream().map(SysOperLog::getOperId).toArray(Long[]::new);
                    sysOperLogService.deleteOperLogByIds(longs);
                } else {
                    keys.forEach(day -> {
                        List<SysOperLog> list = collect.get(day);
                        if (CollectionUtil.isNotEmpty(list)) {
                            ExcelUtil<SysOperLog> util = new ExcelUtil<SysOperLog>(SysOperLog.class);
                            util.exportExcelPath(root + "/" + CacheConstants.LOCAL_CACHING_PATH + CacheConstants.LOCAL_SYS_OPER_LOG_CACHING_PATH + day + ".xlsx", list, "系统操作日志");
                            //删除过期数据
                            Long[] longs = list.stream().map(SysOperLog::getOperId).toArray(Long[]::new);
                            sysOperLogService.deleteOperLogByIds(longs);
                        }
                    });
                }
            }

            log.info("---------系统操作日志缓存到本地文件定时任务执行结束：共" + sysOperLogs.size() + "条数据---------");


            log.info("---------定时任务调度日志缓存到本地文件定时任务开始执行---------");
            //获取当前时间的0点
            List<SysJobLog> sysJobLogs = iSysJobLogService.getLogByTime(endDate);
            if (sysOperLogs.size() > 0) {
                Map<Date, List<SysJobLog>> jobs = sysJobLogs.stream().collect(Collectors.groupingBy(BaseEntity::getCreateTime));
                Set<Date> keys = jobs.keySet();
                if (keys.size() == 0) {
                    String day = DateUtils.parseDateToStr("yyyyMMdd", new Date());
                    ExcelUtil<SysJobLog> util = new ExcelUtil<SysJobLog>(SysJobLog.class);
                    util.exportExcelPath(root + "/" + CacheConstants.LOCAL_CACHING_PATH + CacheConstants.LOCAL_SYS_JOB_LOG_CACHING_PATH + day + ".xlsx", sysJobLogs, "定时任务调度日志");
                    //删除过期数据
                    Long[] longs = sysOperLogs.stream().map(SysOperLog::getOperId).toArray(Long[]::new);
                    iSysJobLogService.deleteJobLogByIds(longs);
                } else {
                    keys.forEach(day -> {
                        List<SysJobLog> list = jobs.get(day);
                        if (CollectionUtil.isNotEmpty(list)) {
                            ExcelUtil<SysJobLog> util = new ExcelUtil<SysJobLog>(SysJobLog.class);
                            util.exportExcelPath(root + "/" + CacheConstants.LOCAL_CACHING_PATH + CacheConstants.LOCAL_SYS_JOB_LOG_CACHING_PATH + day + ".xlsx", list, "定时任务调度日志");
                            //删除过期数据
                            Long[] longs = list.stream().map(SysJobLog::getJobLogId).toArray(Long[]::new);
                            iSysJobLogService.deleteJobLogByIds(longs);
                        }
                    });
                }
            }

            log.info("---------定时任务调度日志缓存到本地文件定时任务执行结束：共" + sysJobLogs.size() + "条数据---------");
        }
    }

    /**
     * 数据同步记录缓存到本地文件
     * 将dayNum天前的查出来保存在一个文件里，然后删掉dayNum天前的数据，
     *
     * @param dayNum
     */
    public void syncLog(Long dayNum) {
        log.info("---------数据同步记录缓存到本地文件定时任务开始执行---------");
        List<String> ips = IpUtils.getInnerHostIp();
        if (iSysConfigService.sysJobAuthIp(20L, ips)) {
            String day = DateUtils.parseDateToStr("yyyyMMdd", new Date());
            Long count = 0L;
            Long total = 0L;
            Integer i = 1;
            String time = DateUtils.parseDateToStr("hhMMss", new Date());
            String root = System.getProperty("user.dir");
            do {
                count = RedisSetUtil.count(CacheConstants.SYNC_DATA_DONE_SUCCESS_KEY);
                if (count == 0) {
                    break;
                }
                List<SyncData> list = new ArrayList<>();
                Long end = count <= dayNum ? count - 1 : dayNum;
                Set<Object> result = RedisSetUtil.queryByRange(CacheConstants.SYNC_DATA_DONE_SUCCESS_KEY, null, end);
                result.forEach(item -> {
                    list.add((SyncData) item);
                });
                ExcelUtil<SyncData> util = new ExcelUtil<SyncData>(SyncData.class);
                util.exportExcelPath(root + "/" + CacheConstants.LOCAL_CACHING_PATH + CacheConstants.LOCAL_SYNC_DATA_CACHING_PATH + day + "/" + time + "_" + i + ".xlsx", list, "同步数据");
                RedisSetUtil.removeRange(CacheConstants.SYNC_DATA_DONE_SUCCESS_KEY, 0L, end);
                total = total + end + 1;
                i++;
            } while (count > 0);

//        List<SyncData> list = syncDataService.list(new LambdaQueryWrapper<SyncData>()
//                .le(SyncData::getCreatedate, one)
//        );
            //删除过期数据
//        if (CollectionUtil.isNotEmpty(list)) {
//            syncDataService.removeBatchByIds(list);
//        }

            log.info("---------数据同步记录缓存到本地文件定时任务执行结束：共" + total + "条数据---------");

        }
    }

}
