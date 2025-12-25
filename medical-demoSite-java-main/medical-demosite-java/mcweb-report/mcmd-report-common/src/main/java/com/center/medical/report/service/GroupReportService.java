package com.center.medical.report.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Report;
import com.center.medical.report.bean.param.CReprotNewDParam;
import com.center.medical.report.bean.param.UploadWordParam;

import java.io.IOException;

/**
 * BG报告主表(Report)表服务接口
 *
 * @author ay
 * @since 2023-04-21 16:55:19
 */
public interface GroupReportService extends IService<Report> {


    /**
     * 综合分析生成报告
     * @param param
     * @return
     */
    Boolean createReprotNewD(CReprotNewDParam param);

    /**
     * 上传word
     * @param param
     * @return
     */
    Boolean uploadWord(UploadWordParam param) throws IOException;
}

