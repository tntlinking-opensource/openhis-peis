package com.center.medical.report.task;

import com.center.medical.bean.enums.NoticeConfigId;
import com.center.medical.bean.param.AddNotificationParam;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.report.service.ReportService;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.service.ISysConfigService;
import com.center.medical.system.service.SysNoticeConfigService;
import com.center.medical.system.service.SysNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 报告通知的定时任务
 */
@Component("reportNoticeTask")
public class ReportNoticeTask {

    private static final Logger log = LoggerFactory.getLogger(ReportCodingTask.class);
    @Resource
    private PeispatientService peispatientService;

    @Resource
    private SysNotificationService sysNotificationService;

    @Resource
    private SysNoticeConfigService sysNoticeConfigService;

    @Resource
    private ReportService reportService;

    @Resource
    private ISysConfigService iSysConfigService;

    /**
     * 报告赋码并重新生成报告
     *
     */
    public void start(){
        log.info("报告通知开始！");
        //发送总检通知，查询分检完成的
        Long totalInspectionCount = reportService.getNotTotalInspectionCount();
        if (totalInspectionCount > 0){
            addNotice(NoticeConfigId.REPORT_TOTAL_INSPECTION.value(),String.valueOf(totalInspectionCount));
        }

        //发送报告未出通知
        Long reportNotReleasedCount = reportService.getReportNotReleasedCount();
        if (reportNotReleasedCount > 0){
            addNotice(NoticeConfigId.REPORT_NOT_RELEASED.value(),String.valueOf(reportNotReleasedCount));
        }

        //报告未出超时通知
        String num = iSysConfigService.selectConfigByKey(Constants.REPORT_NOT_RELEASED_TIME);
        if (StringUtils.isNotEmpty(num)) {
            String codes = reportService.getReportNotReleasedTime(num);
            addNotice(NoticeConfigId.REPORT_NOT_RELEASED_TIMEOUT.value(),codes);
        }


        log.info("报告通知结束！");
    }

    /**
     * 添加通知
     * @param id
     * @param count
     */
    private void addNotice(String id , String value) {
        if (StringUtils.isEmpty(value)){
            return;
        }
        //获取需要发送通知的用户编号
        List<String> userNos = sysNoticeConfigService.getUserNoById(id);
        //添加消息通知
        for (String userNo : userNos) {
            AddNotificationParam param = new AddNotificationParam(userNo, id, value);
            sysNotificationService.addNotice(param);
        }
    }


}
