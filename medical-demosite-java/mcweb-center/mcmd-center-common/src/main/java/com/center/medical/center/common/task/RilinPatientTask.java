package com.center.medical.center.common.task;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.center.medical.center.common.bean.model.RilinSyncResult;
import com.center.medical.center.common.constant.RilinConstant;
import com.center.medical.center.common.dao.RilinSyncResultMapper;
import com.center.medical.center.common.service.RilinSyncTimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 对接瑞林萨尔健康管理系统,体检结果同步定时任务
 * @author xhp
 * @since 2025-03-24 8:33
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RilinPatientTask {
    private final RilinSyncTimeService rilinSyncTimeService;
    private final RilinSyncResultMapper rilinSyncResultMapper;

    @Scheduled(cron = "${rilin.cron.patient:-}")
    public void sync(){
        try{
            rilinSyncTimeService.syncPatient();
        }catch (Exception e){
            log.error(RilinConstant.PATIENT+RilinConstant.REMARK_FAILED,e);

            //记录上传失败
            RilinSyncResult rilinSyncResult=new RilinSyncResult();
            Date currentDate=new Date();
            rilinSyncResult.setIsSuccess(0);
            rilinSyncResult.setErrorMsg(ExceptionUtil.stacktraceToString(e,RilinConstant.ERROR_MSG_LIMIT));
            rilinSyncResult.setTableName(RilinConstant.PATIENT);
            rilinSyncResult.setEndTime(currentDate);
            rilinSyncResult.setRemarks(RilinConstant.REMARK_FAILED);
            rilinSyncResult.setCreatedate(currentDate);
            rilinSyncResultMapper.insert(rilinSyncResult);
        }

    }
}
