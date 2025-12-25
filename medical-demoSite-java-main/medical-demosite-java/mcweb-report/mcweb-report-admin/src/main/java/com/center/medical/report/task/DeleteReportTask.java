package com.center.medical.report.task;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.model.OtherReport;
import com.center.medical.bean.model.Report;
import com.center.medical.report.service.ReportService;
import com.center.medical.service.AttachmentService;
import com.center.medical.service.OtherReportService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 删除正式报告定时任务
 */
@Component("DeleteReportTask")
public class DeleteReportTask {

    @Resource
    private ReportService reportService;

    @Resource
    private OtherReportService otherReportService;

    @Resource
    private AttachmentService attachmentService;

    /**
     * 删除正式报告定时任务
     *
     */
    public void start(){
        System.out.println("删除正式报告定时任务执行中--------------");


        // 将当前日期减去30天的00点
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minus30Days = now.minus(30, ChronoUnit.DAYS);
        LocalDateTime result = LocalDateTime.of(minus30Days.toLocalDate(), LocalTime.MIN);

        System.out.println("开始删除正式报告--------------");
        //查询pdf地址不为空的，30天之前的数据
        List<Report> reportList = reportService.list(new LambdaQueryWrapper<Report>()
                .isNotNull(Report::getUrlPdf)
                .le(Report::getCreatedate, result));

        //删除文件，并把地址清空
        if (CollectionUtil.isNotEmpty(reportList)){
            for (Report report : reportList) {
                attachmentService.deleteFile(report.getUrlPdf());
                report.setUrlPdf("");
            }
            reportService.updateBatchById(reportList);
        }

        System.out.println("开始删除临时报告--------------");
        List<OtherReport> otherReportList = otherReportService.list(new LambdaQueryWrapper<OtherReport>()
                .isNotNull(OtherReport::getPdfUrl)
                .le(OtherReport::getCreatedate, result));
        if (CollectionUtil.isNotEmpty(otherReportList)){
            for (OtherReport otherReport : otherReportList) {
                attachmentService.deleteFile(otherReport.getPdfUrl());
                otherReport.setPdfUrl("");
            }
            otherReportService.updateBatchById(otherReportList);
        }



        System.out.println("删除正式报告定时任务执行中任务成功！！！！");


    }






}
