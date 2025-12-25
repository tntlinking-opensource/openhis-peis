package com.center.medical.platform.task;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.thread.CustomThreadPool;
import com.center.medical.common.utils.ip.IpUtils;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.finance.bean.model.KdRemittance;
import com.center.medical.finance.service.KdRemittanceService;
import com.center.medical.reception.service.RegisterService;
import com.center.medical.reception.service.SyncCommonService;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.reservation.bean.model.ReservationSetting;
import com.center.medical.reservation.service.ReservationService;
import com.center.medical.reservation.service.ReservationSettingService;
import com.center.medical.sellcrm.bean.model.Peisorgreservationreceipt;
import com.center.medical.sellcrm.service.PeisorgreservationreceiptService;
import com.center.medical.sync.bean.model.SyncData;
import com.center.medical.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author: 路飞
 * @date: 2023-02-07 10:46
 * @description: 公共定时处理任务
 */
@Slf4j
@Component("commonTask")
@DisallowConcurrentExecution
public class CommonTask {
    @Resource
    private ISysConfigService iSysConfigService;
    @Resource
    private KdRemittanceService kdRemittanceService;
    @Resource
    private RegisterService registerService;
    @Resource
    private SyncCommonService syncCommonService;
    private final ExecutorService lisExecutorService = CustomThreadPool.createFixedThreadPool(2, 40);
    @Resource
    private ReservationSettingService reservationSettingService;
    @Resource
    private ReservationService reservationService;
    @Resource
    private PeisorgreservationreceiptService peisorgreservationreceiptService;

    /**
     * 定时处理任务
     *
     * @param duringTime 定时任务执行的间隔时间，单位：秒
     */
    public void deal(Integer duringTime, Long jobId) {
        log.info("开始定时处理任务（{}）：commonTask.deal", duringTime, jobId);
        List<String> ips = IpUtils.getInnerHostIp();
        if (iSysConfigService.sysJobAuthIp(jobId, ips)) {
            DateTime now = DateUtil.date();
            DateTime lastDealTime = now.offsetNew(DateField.SECOND, -(duringTime + 30));
            log.info("{}S前的时间: {}", duringTime, lastDealTime);

            //获取上次执行后更新的银行流水数据
            List<KdRemittance> kdRemittances = kdRemittanceService.list(new LambdaQueryWrapper<KdRemittance>().ge(KdRemittance::getModifydate, lastDealTime).eq(KdRemittance::getOnline, 1));
            //获取上次执行后最新创建的体检者
//            List<Peispatient> patients = registerService.list(new LambdaQueryWrapper<Peispatient>().ge(Peispatient::getCreatedate, lastDealTime).eq(Peispatient::getFRegistered, 0));
            //获取上次执行后最新预约设置
            List<ReservationSetting> reservationSettingList = reservationSettingService.list(new LambdaQueryWrapper<ReservationSetting>().ge(ReservationSetting::getModifydate, lastDealTime));
            //获取上次执行后最新预约信息
            List<Reservation> reservationList = reservationService.list(new LambdaQueryWrapper<Reservation>().ge(Reservation::getModifydate, lastDealTime));
            //获取上次执行后最新的发票信息
            List<Peisorgreservationreceipt> peisorgreservationreceipts = peisorgreservationreceiptService.list(new LambdaQueryWrapper<Peisorgreservationreceipt>().ge(Peisorgreservationreceipt::getModifydate, lastDealTime));

            log.info("定时处理任务，进入线程池...");
            lisExecutorService.submit(() -> {
                //1.同步最新的银行流水
                //手动执行同步
                for (KdRemittance kr : kdRemittances) {
                    //执行同步
                    SyncData syncData = new SyncData();
                    syncData.setOptType(2);
                    syncData.setBizDb("medical_prod");
                    syncData.setBizTable("kd_remittance");
                    syncData.setBizModifydate(new Date());
                    syncData.setIsAll(0);
                    syncData.setStatus(0);
                    syncData.setBizId(kr.getFid());
                    RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData, DateUtil.currentSeconds());
                    log.info("执行KdRemittance同步成功: {}", syncData);
                }

                //2.同步最新登记的体检者
//                if (CollectionUtil.isNotEmpty(patients)) {
//                    List<String> patientCodes = patients.stream().map(Peispatient::getPatientcode).collect(Collectors.toList());
//                    syncCommonService.syncPatientData(patientCodes);
//                }

                //3.同步预约设置
                for (ReservationSetting reservationSetting : reservationSettingList) {
                    //执行同步
                    SyncData syncData = new SyncData();
                    syncData.setOptType(2);
                    syncData.setBizDb("medical_prod");
                    syncData.setBizTable("md_reservation_setting");
                    syncData.setBizModifydate(new Date());
                    syncData.setIsAll(0);
                    syncData.setStatus(0);
                    syncData.setBizId(reservationSetting.getId());
                    RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData, DateUtil.currentSeconds());
                    log.info("执行reservationSetting同步成功: {}", syncData);
                }
                //4.同步预约信息
                for (Reservation reservation : reservationList) {
                    //执行同步
                    SyncData syncData = new SyncData();
                    syncData.setOptType(2);
                    syncData.setBizDb("medical_prod");
                    syncData.setBizTable("md_reservation");
                    syncData.setBizModifydate(new Date());
                    syncData.setIsAll(0);
                    syncData.setStatus(0);
                    syncData.setBizId(reservation.getId());
                    RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData, DateUtil.currentSeconds());
                    log.info("执行reservation同步成功: {}", syncData);
                }
                //5.同步发票信息
                for (Peisorgreservationreceipt peisorgreservationreceipt : peisorgreservationreceipts) {
                    //执行同步
                    SyncData syncData = new SyncData();
                    syncData.setOptType(2);
                    syncData.setBizDb("medical_prod");
                    syncData.setBizTable("md_peisorgreservationreceipt");
                    syncData.setBizModifydate(new Date());
                    syncData.setIsAll(0);
                    syncData.setStatus(0);
                    syncData.setBizId(peisorgreservationreceipt.getId());
                    RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData, DateUtil.currentSeconds());
                }
                log.info("定时处理任务，线程结束...");
            });
        }
    }

}
