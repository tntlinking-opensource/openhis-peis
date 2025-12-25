package com.center.medical.center.common.task;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.center.medical.center.common.bean.model.RilinSyncResult;
import com.center.medical.center.common.constant.RilinConstant;
import com.center.medical.center.common.dao.RilinSyncResultMapper;
import com.center.medical.center.common.service.RilinRiskclientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 对接瑞林萨尔健康管理系统,重大阳性同步
 * @author xhp
 * @since 2025-03-25 8:19
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RilinRiskTask {
    private final RilinRiskclientService rilinRiskclientService;
    private final RilinSyncResultMapper rilinSyncResultMapper;

    /**
     * 实时同步，无并发
     */
    @Scheduled(cron = "${rilin.cron.risk:-}")
    public void sync(){
        try{
            rilinRiskclientService.sync();
        }catch (Exception e){
            log.error(RilinConstant.RISK+RilinConstant.REMARK_FAILED,e);

            //记录上传失败
            RilinSyncResult rilinSyncResult=new RilinSyncResult();
            Date currentDate=new Date();
            rilinSyncResult.setIsSuccess(0);
            rilinSyncResult.setErrorMsg(ExceptionUtil.stacktraceToString(e,RilinConstant.ERROR_MSG_LIMIT));
            rilinSyncResult.setTableName(RilinConstant.RISK);
            rilinSyncResult.setEndTime(currentDate);
            rilinSyncResult.setRemarks(RilinConstant.REMARK_FAILED);
            rilinSyncResult.setCreatedate(currentDate);
            rilinSyncResultMapper.insert(rilinSyncResult);
        }
    }
}
