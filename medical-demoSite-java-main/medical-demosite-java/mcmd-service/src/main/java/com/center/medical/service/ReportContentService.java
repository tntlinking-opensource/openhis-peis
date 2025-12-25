package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.ReportContent;
import com.center.medical.bean.param.ReportContentParam;

import java.util.List;

/**
 * 生成报告内容(ReportContent)表服务接口
 *
 * @author ay
 * @since 2023-05-18 10:43:56
 */
public interface ReportContentService extends IService<ReportContent> {


    /**
     * 创建报告内容
     *
     * @param content         报告内容
     * @param reportType      报告类型，0.检验报告 1.老人查体-分析报告 2.隐私报告 3.临时报告 4.团检报告 5.个检报告 6.对比报告 7.Pacs报告 8.职业结果告知书 9.检验报告(除检验科的)
     * @param patientcode     体检号
     * @param idExamtype      体检类型,0.健康体检 1.职业体检
     * @param analyzeId       团价样本id，md_ball_check_report的id
     * @param compareReportId 对比报告id
     */
    void createReportContent(String content, Integer reportType, String patientcode,
                             String idExamtype, String analyzeId, String compareReportId, String ksId, List<String> reportPicList);

    /**
     * 查询报告内容
     *
     * @param param
     * @return
     */
    ReportContent findReport(ReportContentParam param);
}

