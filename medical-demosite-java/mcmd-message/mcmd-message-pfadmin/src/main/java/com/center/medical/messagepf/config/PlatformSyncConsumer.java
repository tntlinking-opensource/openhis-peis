package com.center.medical.messagepf.config;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.message.constant.MessageConstant;
import com.center.medical.sync.bean.dto.SyncDataDto;
import com.center.medical.sync.service.SyncSqlRunService;
import com.center.medical.system.service.ISysBranchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * 线上数据同步消息队列-消费者
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:58
 */
@Slf4j
@Component
public class PlatformSyncConsumer {

    @Autowired
    private SyncSqlRunService syncSqlRunService;
    @Autowired
    private ISysBranchService iSysBranchService;

    /**
     * 消费线下分中心实时同步上来的的数据
     *
     * @param message
     */
    @RabbitListener(queues = MessageConstant.LC_SYNC_DATA_QUEUE)
    public void receiveSyncData(String message) {
//        log.info("--接收线下系统推送的实时同步数据message:{}--", message);
        SyncDataDto item = null;
        try {
            item = JSONUtil.toBean(message, SyncDataDto.class);
        } catch (Exception e) {
            log.error("接收线下系统推送的同步数据失败，数据不合法:{}、{}", message, e.getMessage());
        }
        if (Objects.nonNull(item)) {
            SysBranch branch = iSysBranchService.getByBranchId(item.getFxzId());
            if (Objects.isNull(branch)) {
                log.error("接收线下系统推送的实时同步数据失败，分中心不存在或者已经禁用！分中心：{}", item.getFxzId());
            } else {
                execSaOrUp(message, item, 0);
            }
        }
    }

    /**
     * 消费线下分中心闲时同步上来的的数据
     *
     * @param message
     */
    @RabbitListener(queues = MessageConstant.LC_SYNC_DATA_DELAY_QUEUE)
    public void receiveSyncDelayData(String message) {
//        log.info("接收线下系统推送的闲时同步数据message：{}", message);
        SyncDataDto item = null;
        try {
            item = JSONUtil.toBean(message, SyncDataDto.class);
        } catch (Exception e) {
            log.error("接收线下系统推送的闲时同步数据失败，数据不合法:{}、{}", message, e.getMessage());
        }
        if (Objects.nonNull(item)) {
            SysBranch branch = iSysBranchService.getByBranchId(item.getFxzId());
            if (Objects.isNull(branch)) {
                log.error("接收线下系统推送的闲时同步数据失败，分中心不存在或者已经禁用！分中心：{}", item.getFxzId());
            } else {
                execSaOrUp(message, item, 0);
            }
        }
    }

    /**
     * 执行同步数据操作
     *
     * @param message
     * @param item
     * @param count   同一条数据执行的次数
     * @return
     */
    private Boolean execSaOrUp(String message, SyncDataDto item, Integer count) {
        try {
            if (item.getOptType() == 2 || item.getOptType() == 3) {
                // 新增或者更新操作
                Map<String, Object> sqlData = JSONUtil.toBean(item.getSyncRunSql(), Map.class);
                syncSqlRunService.updateOrInsert(item.getBizTable(), sqlData);
            } else {
                // 删除操作
                syncSqlRunService.updateSql(item.getSyncRunSql());
            }
            log.info("接收线下系统推送的同步数据成功:{}、{}", message);
//            RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_DONE_SUCCESS_KEY, message, DateUtil.currentSeconds());

        } catch (Exception e) {
            if (count < 3) {
                log.error("接收线下系统推送的同步数据，执行失败{}次，再执行一次:{}、{}", count, message, e);
                execSaOrUp(message, item, count + 1);
            } else {
                log.error("接收线下系统推送的同步数据失败:{}、{}", message, e);
                RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_DONE_FAIL_KEY, message, DateUtil.currentSeconds());
            }

        }
        return Boolean.TRUE;
    }
}
