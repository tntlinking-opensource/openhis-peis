package com.center.medical.abteilung.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.abteilung.bean.model.SectionResultPlan;
import com.center.medical.abteilung.service.SectionResultPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 批量录入审核的定时任务
 */
@Component("sectionResultPlanTask")
public class SectionResultPlanTask {

    private static final Logger log = LoggerFactory.getLogger(SectionResultPlanTask.class);

    @Resource
    private SectionResultPlanService sectionResultPlanService;

    /**
     * 报告赋码并重新生成报告
     *
     */
    public void start(){
        log.info("批量录入审核定时任务开始！");

        List<SectionResultPlan> list = sectionResultPlanService.list(new LambdaQueryWrapper<SectionResultPlan>()
                .eq(SectionResultPlan::getStatus, 0)
                .isNull(SectionResultPlan::getErrorMsg)
        );
        for (SectionResultPlan sectionResultPlan : list) {
            try {
                sectionResultPlanService.audit(sectionResultPlan);
                log.info("JobSectionResultPlan"+sectionResultPlan.getPatientcode()+"审核成功！");
            } catch (Exception e) {
                log.error("JobSectionResultPlan"+sectionResultPlan.getPatientcode()+"审核失败！");
                e.printStackTrace();
                System.out.println("--------"+e.getMessage());
                sectionResultPlan.setErrorMsg(e.getMessage());
                sectionResultPlanService.updateById(sectionResultPlan);
            }
        }
        log.info("批量录入审核定时任务结束！");
    }




}
