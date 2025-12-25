package com.center.medical.messagelc.config;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.center.medical.bean.model.*;
import com.center.medical.common.config.Domain;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.message.bean.dto.UpdateCondition;
import com.center.medical.service.*;
import com.center.medical.sync.bean.dto.SyncDataDto;
import com.center.medical.sync.bean.model.SyncFile;
import com.center.medical.sync.service.SyncSqlRunService;
import com.center.medical.system.bean.model.SysTableConfig;
import com.center.medical.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 线下数据同步消息队列-消费者
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:58
 */
@Slf4j
@Component
public class LocalSyncConsumer {

    @Autowired
    private SyncSqlRunService syncSqlRunService;
    @Autowired
    private ISysConfigService iSysConfigService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private PeispatientService peispatientService;
    @Autowired
    private PeispatientfeeitemService peispatientfeeitemService;
    @Autowired
    private PeispatientChargeMainService peispatientChargeMainService;
    @Autowired
    private PeispatientReservationChargeService peispatientReservationChargeService;
    @Autowired
    private PeispatientchargeService peispatientchargeService;

    /**
     * 线上系统同步到当前分中心的数据的消息消费者
     *
     * @param message
     */
    @RabbitListener(queues = "${sync.amq.offline.queue.data}")
    public void receiveSyncData(String message) {
        // log.info("接收到线上系统同步下来的数据{}", message);
        SyncDataDto item = null;
        try {
            item = JSONUtil.toBean(message, SyncDataDto.class);
        } catch (Exception e) {
            log.error("接收到线上系统同步下来的数据失败，数据不合法:{}、{}", message, e.getMessage());
        }
        if (Objects.nonNull(item)) {
//            if (canExec(item)) {
                execSaOrUp(message, item, 0);
//            }
//            try {
//                if (item.getOptType() == 2 || item.getOptType() == 3) {
//                    //新增或者更新操作
//                    Map<String, Object> sqlData = JSONUtil.toBean(item.getSyncRunSql(), Map.class);
//                    syncSqlRunService.updateOrInsert(item.getBizTable(), sqlData);
//                } else {
//                    //删除操作
//                    syncSqlRunService.updateSql(item.getSyncRunSql());
//                }
//                RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_DONE_SUCCESS_KEY, message, DateUtil.currentSeconds());
//            } catch (Exception e) {
//                log.error("接收到线上系统同步下来的数据失败:{}、{}", message, e);
//                RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_DONE_FAIL_KEY, message, DateUtil.currentSeconds());
//            }
        }

    }

    /**
     * 线判断是否需要覆盖
     * @param item
     * @return
     */
    private boolean canExec(SyncDataDto item) {
        //如果是体检者表、体检者收费项目表、体检者收费主表、体检者收费记录表、体检者结算表等这些表需要线判断是否需要覆盖：如果已登记则不需要覆盖
        List<String> tables = Arrays.asList("md_peispatient", "md_peispatientfeeitem", "md_peispatient_charge_main", "md_peispatient_reservation_charge", "md_peispatientcharge");
        Boolean result = Boolean.TRUE;
        if (item.getOptType() == 4){
            return Boolean.TRUE;
        }
        if (StringUtils.isBlank(item.getBizId())||StringUtils.isBlank(item.getSyncRunSql())){
            log.warn("同步数据不合法：{}", item);
            return Boolean.FALSE;
        }
        if (tables.contains(item.getBizTable())){
            String bizId = item.getBizId();
            switch (item.getBizTable()){
                case "md_peispatient":
                    //判断体检者表是否已登记
                    Peispatient peispatient = peispatientService.getInfoById(bizId);
                    if (Objects.nonNull(peispatient) && peispatient.getFRegistered() == 1){
                        log.warn("体检者已登记，不能覆盖：{}", item);
                        result = Boolean.FALSE;
                    }
                    break;
                case "md_peispatientfeeitem":
                    //判断体检者收费项目表是否已登记
                    Peispatientfeeitem peispatientfeeitem = peispatientfeeitemService.getInfoById(bizId);
                    if (Objects.nonNull(peispatientfeeitem) && peispatientfeeitem.getFRegistered() == 1){
                        result = Boolean.FALSE;
                    }
                    break;
                case "md_peispatient_charge_main":
                    //判断体检者收费主表是否已登记
                    PeispatientChargeMain peispatientChargeMain = peispatientChargeMainService.getInfoById(bizId);
                    if (Objects.nonNull(peispatientChargeMain)){
                        Peispatient byPatientCode = peispatientService.getByPatientCode(peispatientChargeMain.getPatientcode());
                        if (Objects.isNull(byPatientCode)){
                            result = Boolean.FALSE;
                        }else  if (byPatientCode.getFRegistered() == 1){
                            result = Boolean.FALSE;
                        }
                    }
                    break;
                case "md_peispatient_reservation_charge":
                    //判断体检者收费主表是否已登记
                    PeispatientReservationCharge peispatientReservationCharge = peispatientReservationChargeService.getInfoById(bizId);
                    if (Objects.nonNull(peispatientReservationCharge)){
                        Peispatient byPatientCode = peispatientService.getByPatientCode(peispatientReservationCharge.getPatientcode());
                        if (Objects.isNull(byPatientCode)){
                            result = Boolean.FALSE;
                        }else  if (byPatientCode.getFRegistered() == 1){
                            result = Boolean.FALSE;
                        }
                    }
                    break;
                case "md_peispatientcharge":
                    //判断体检者收费主表是否已登记
                    Peispatientcharge peispatientcharge = peispatientchargeService.getInfoById(bizId);
                    if (Objects.nonNull(peispatientcharge)){
                        Peispatient byPatientCode = peispatientService.getByPatientCode(peispatientcharge.getPatientcode());
                        if (Objects.isNull(byPatientCode)){
                            result = Boolean.FALSE;
                        }else  if (byPatientCode.getFRegistered() == 1){
                            result = Boolean.FALSE;
                        }
                    }
                    break;
                default:
            }
        }
        return result;
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
                //如何更新操作的话判断是否允许进行更新操作
//                if (item.getOptType() == 3){
//                    SysTableConfig tc = sysTableConfigService.getInfoByTN(item.getBizTable());//获取当前操作的数据表配置信息
//                    if (!needEexecuteUpdate(tc, sqlData)){
//                        //不允许执行更新覆盖当前数据操作
//                        return Boolean.FALSE;
//                    }
//                }
                syncSqlRunService.updateOrInsert(item.getBizTable(), sqlData);
            } else {
                // 删除操作
                syncSqlRunService.updateSql(item.getSyncRunSql());
            }
//            RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_DONE_SUCCESS_KEY, message, DateUtil.currentSeconds());
            log.info("接收到线上系统同步下来的数据成功:{}", message);

        } catch (Exception e) {
            if (count < 3) {
                log.error("接收到线上系统同步下来的数据，执行失败{}次，再执行一次:{}、{}", count, message, e);
                execSaOrUp(message, item, count + 1);
            } else {
                log.error("接收到线上系统同步下来的数据失败:{}、{}", message, e);
                RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_DONE_FAIL_KEY, message, DateUtil.currentSeconds());
            }

        }
        return Boolean.TRUE;
    }

    /**
     * 线上系统同步到当前分中心的文件的消息消费者
     *
     * @param message
     */
    @RabbitListener(queues = "${sync.amq.offline.queue.file1}", concurrency = "3")
    public void downloadSyncFile(String message) {
        log.info("接收到线上系统同步下来的文件");
        SyncFile item = null;
        try {
            item = JSONUtil.toBean(message, SyncFile.class);
        } catch (Exception e) {
            log.error("接收到线上系统同步下来的文件失败，，数据不合法:{}、{}", message, e.getMessage());
        }
        if (Objects.nonNull(item)) {
            Domain domain = iSysConfigService.getDomain();
            String host = domain.getRsPfDomain();
            try {
                attachmentService.uploadSyncFile(host, item.getImageUrl(), 0);
                item.setStatus(2);
//                RedisSetUtil.addToSortedSet(CacheConstants.SYNC_FILE_DOWN_DONE_KEY, item, DateUtil.currentSeconds());
                log.info("接收到线上系统同步下来的文件成功:{}", message);
            } catch (IOException e) {
                //保存失败
                log.error("接收到线上系统同步下来的文件失败:{}、{}", item, e);
                item.setStatus(3);
                RedisSetUtil.addToSortedSet(CacheConstants.SYNC_FILE_DOWN_FAILED_KEY, item, DateUtil.currentSeconds());
            }
        }
    }

    /**
     * 当前分中心的文件同步到线上的消息消费者
     *
     * @param message
     */
    @RabbitListener(queues = "${sync.amq.offline.queue.file2}", concurrency = "3")
    public void uploadSyncFile(String message) {
        log.info("接收到当前分中心同步线上的文件{}");
        SyncFile item = null;
        try {
            item = JSONUtil.toBean(message, SyncFile.class);
        } catch (Exception e) {
            log.error("接收到当前分中心同步线上的文件失败，数据不合法:{}、{}", message, e.getMessage());
        }
        if (Objects.nonNull(item)) {
            Domain domain = iSysConfigService.getDomain();
            // log.info("domain:{}", domain);
            String host = domain.getRsLcDomain();
            try {
                attachmentService.uploadSyncFile(host, item.getImageUrl(), 1);
                item.setStatus(2);
//                RedisSetUtil.addToSortedSet(CacheConstants.SYNC_FILE_UP_DONE_KEY, item, DateUtil.currentSeconds());
                log.info("当前分中心的文件同步到线上成功:{}", message);
            } catch (IOException e) {
                log.error("同步线上的文件失败:{}、{}", item, e);
                item.setStatus(3);
                RedisSetUtil.addToSortedSet(CacheConstants.SYNC_FILE_UP_FAILED_KEY, item, DateUtil.currentSeconds());
            }
        }
    }



    /**
     * 判断是否允许更新操作
     *
     * @param tc  表配置信息
     * @param map 同步数据
     * @return
     */
    private boolean needEexecuteUpdate(SysTableConfig tc, Map<String, Object> map) {
        // log.info("判断是否达到了需要同步的条件.needEexecuteSync:{}、{}", tc, map);
        Boolean result = Boolean.TRUE;
        //判断是否是需要判断条件的表
        if (StringUtils.isNotBlank(tc.getUpdateCondition())) {
            UpdateCondition updateCondition = JSONUtil.toBean(tc.getUpdateCondition(), UpdateCondition.class);
            String keyValue = updateCondition.getConditionKeyType()==1? "'"+map.get(tc.getCurKeyName())+"'":(String) map.get(tc.getCurKeyName());
            //查询当前数据中对应的数据
            String selectSql = String.format("SELECT "+updateCondition.getValueNames()+" FROM " + updateCondition.getTableName() + " WHERE " + tc.getKeyName() + " = " + keyValue);
            log.info("判断是否允许同步更新操作tc:{}，selectSql：{}", tc,selectSql);
            List<Map<String, Object>> updateItemDbList = syncSqlRunService.queryForList(selectSql); //查询更新的记录
            for (Map<String, Object> curUpdateItemDb : updateItemDbList) {
                log.info("判断是否允许同步更新操作，curUpdateItemDb：{}", curUpdateItemDb);
                if (Objects.nonNull(curUpdateItemDb)) {
                    switch (updateCondition.getCurTableName()) {
                        case "md_peispatient":
                        case "md_peispatientfeeitem":
                        case "md_peispatient_charge_main":
                        case "md_peispatient_charge_other":
                        case "md_peispatient_charge_record":
                            if ((Integer) curUpdateItemDb.get("f_registered") == 1) {
                                result = Boolean.FALSE;
                            } else {
                                result = Boolean.TRUE;
                            }
                            break;
                        case "md_peispatientcharge":
                            if ((Integer) curUpdateItemDb.get("f_feecharged") == 1) {
                                result = Boolean.FALSE;
                            } else {
                                result = Boolean.TRUE;
                            }
                            break;
                        default:
                            result = Boolean.TRUE;
                    }
                }else {
                    result = Boolean.TRUE;
                }
            }

        } else {
            result = Boolean.TRUE;
        }
        log.info("判断是否允许同步更新操作，result：{}", result);
        return result;
    }
}
