package com.center.medical.report.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Report;
import com.center.medical.report.bean.vo.PrivateReportVo;

import java.io.IOException;

/**
 * 隐私报告(Report)表服务接口
 *
 * @author ay
 * @since 2023-04-26 17:43:49
 */
public interface PrivateReportService extends IService<Report> {


    /**
     * 隐私报告生成
     * @param patientcode
     * @return
     */
    PrivateReportVo create(String patientcode);

    /**
     * 生成隐私报告pdf
     * @param patientcode
     * @return
     */
    String createPdf(String patientcode) throws IOException;
}

