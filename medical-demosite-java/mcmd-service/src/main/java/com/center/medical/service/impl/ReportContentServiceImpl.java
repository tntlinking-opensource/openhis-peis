package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.ReportContent;
import com.center.medical.bean.param.ReportContentParam;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.ReportContentMapper;
import com.center.medical.service.ReportContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 生成报告内容(ReportContent)表服务实现类
 *
 * @author ay
 * @since 2023-05-18 10:43:56
 */
@Slf4j
@Service("reportContentService")
@RequiredArgsConstructor
public class ReportContentServiceImpl extends ServiceImpl<ReportContentMapper, ReportContent> implements ReportContentService {

    private final ReportContentMapper reportContentMapper;
    private final PeispatientMapper peispatientMapper;


    /**
     * 创建报告内容
     *
     * @param content
     */
    @Override
    public void createReportContent(String content, Integer reportType, String patientcode, String idExamtype, String analyzeId, String compareReportId, String ksId, List<String> reportPicList) {
        ReportContentParam param = new ReportContentParam(patientcode, idExamtype, reportType, analyzeId, compareReportId, ksId);
        //有就更新,没有就插入
        ReportContent reportContent = reportContentMapper.findReport(param);


        if (ObjectUtils.isNotEmpty(reportContent)) {
            if (reportContent.getCheckStatus() == 1) {
                throw new ServiceException("已主检审核,不能再生成报告!");
            }
        } else {
            reportContent = new ReportContent();
            reportContent.setCreateBy(SecurityUtils.getUserNo());
            reportContent.setBranchId(SecurityUtils.getCId());
        }
        reportContent.setContent(content);
        reportContent.setReportType(reportType);
        reportContent.setPatientcode(patientcode);
        reportContent.setIdExamtype(idExamtype);
        reportContent.setCheckStatus(0);
        reportContent.setAnalyzeId(analyzeId);
        reportContent.setCompareReportId(compareReportId);
        reportContent.setKsId(ksId);
        reportContent.setPic(ObjectUtils.isNotEmpty(reportPicList)?String.join(",", reportPicList):"");
        this.saveOrUpdate(reportContent);
    }


    /**
     * 查询报告内容
     *
     * @param param
     * @return
     */
    @Override
    public ReportContent findReport(ReportContentParam param) {
        //传职业的可能是复查的
        if (ObjectUtils.isNotEmpty(param.getIdExamtype())
                && ObjectUtils.isNotEmpty(param.getPatientcode())
                && "1".equals(param.getIdExamtype())
                && param.getReportType() != 8
        ) {
            Peispatient peispatient = peispatientMapper.getByPatientCode(param.getPatientcode());
            String idExamtype = peispatient.getIdExamtype();
            if ("3".equals(idExamtype)) {
                param.setIdExamtype("3");
            }
        }
        return reportContentMapper.findReport(param);
    }
}

