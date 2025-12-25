package com.center.medical.admin.task;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Review;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.thread.CustomThreadPool;
import com.center.medical.common.utils.ip.IpUtils;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.finance.bean.model.KdRemittance;
import com.center.medical.finance.service.KdRemittanceService;
import com.center.medical.reception.bean.model.ReviewProject;
import com.center.medical.reception.service.RegisterService;
import com.center.medical.reception.service.ReviewProjectService;
import com.center.medical.reception.service.ReviewService;
import com.center.medical.reception.service.SyncCommonService;
import com.center.medical.report.bean.model.CommentsProgessional;
import com.center.medical.report.service.CommentsProgessionalService;
import com.center.medical.sync.bean.model.SyncData;
import com.center.medical.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

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
    @Resource
    private CommentsProgessionalService commentsProgessionalService;
    @Resource
    private ReviewService reviewService;
    @Resource
    private ReviewProjectService reviewProjectService;


    private final ExecutorService lisExecutorService = CustomThreadPool.createFixedThreadPool(2, 40);

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
            DateTime lastDealTime = now.offsetNew(DateField.SECOND, -(duringTime + 1));
            log.info("{}S前的时间: {}", duringTime, lastDealTime);

            //获取上次执行后更新的银行流水数据
            List<KdRemittance> kdRemittances = kdRemittanceService.list(new LambdaQueryWrapper<KdRemittance>().ge(KdRemittance::getModifydate, lastDealTime).eq(KdRemittance::getOnline, 0));
            //获取上次执行后最新登记的体检者
            List<Peispatient> patients = registerService.list(new LambdaQueryWrapper<Peispatient>().ge(Peispatient::getDateregister, lastDealTime).eq(Peispatient::getFRegistered, 1));
            if (patients.size()>2000){
                log.info("执行公共定时任务失败，当前数据过多，可能不正常，请排查后再执行，{}S前的时间: {}", duringTime, lastDealTime);
                return;
            }
            //职业处理意见表
            List<CommentsProgessional> commentsProgessionalList = commentsProgessionalService.list(new LambdaQueryWrapper<CommentsProgessional>().ge(CommentsProgessional::getModifydate, lastDealTime).eq(CommentsProgessional::getIsDelete, 0));
            log.info("职业处理意见表数量:{}",commentsProgessionalList.size());
            //复查及复查项目
            List<Review> reviewList = reviewService.list(new LambdaQueryWrapper<Review>().ge(Review::getModifydate, lastDealTime).eq(Review::getIsDelete, 0));
            List<ReviewProject> reviewProjectList = reviewProjectService.list(new LambdaQueryWrapper<ReviewProject>().ge(ReviewProject::getModifydate, lastDealTime));
            log.info("复查数量:{}",reviewList.size());


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
                if (CollectionUtil.isNotEmpty(patients)) {
                    List<String> patientCodes = patients.stream().map(Peispatient::getPatientcode).collect(Collectors.toList());
                    syncCommonService.syncPatientData(patientCodes);
                }

                log.info("开始处理职业处理意见表");
                //3.职业处理意见表
                for (CommentsProgessional commentsProgessional : commentsProgessionalList) {
                    //执行同步
                    SyncData syncData = new SyncData();
                    syncData.setOptType(2);
                    syncData.setBizDb("medical_prod");
                    syncData.setBizTable("md_comments_progessional");
                    syncData.setBizModifydate(new Date());
                    syncData.setIsAll(0);
                    syncData.setStatus(0);
                    syncData.setBizId(commentsProgessional.getId());
                    RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData, DateUtil.currentSeconds());
                    log.info("执行md_comments_progessional同步成功: {}", syncData);
                }
                log.info("开始处理复查及复查项目");
                //4.复查及复查项目
                for (Review review : reviewList) {
                    //执行同步
                    SyncData syncData = new SyncData();
                    syncData.setOptType(2);
                    syncData.setBizDb("medical_prod");
                    syncData.setBizTable("md_review");
                    syncData.setBizModifydate(new Date());
                    syncData.setIsAll(0);
                    syncData.setStatus(0);
                    syncData.setBizId(review.getId());
                    RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData, DateUtil.currentSeconds());
                    log.info("执行md_review同步成功: {}", syncData);
                }
                for (ReviewProject reviewProject : reviewProjectList) {
                    //执行同步
                    SyncData syncData = new SyncData();
                    syncData.setOptType(2);
                    syncData.setBizDb("medical_prod");
                    syncData.setBizTable("md_review_project");
                    syncData.setBizModifydate(new Date());
                    syncData.setIsAll(0);
                    syncData.setStatus(0);
                    syncData.setBizId(reviewProject.getId());
                    RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData, DateUtil.currentSeconds());
                    log.info("执行md_review_project同步成功: {}", syncData);
                }
                log.info("定时处理任务，线程结束...");
            });
        }
    }

}
