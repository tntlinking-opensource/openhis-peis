package com.center.medical.report.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Report;
import com.center.medical.report.bean.param.IReportParam;
import com.center.medical.report.bean.vo.CreateReportVo;
import net.sf.jasperreports.engine.JRException;

import java.util.List;

/**
 * BG报告主表(Report)表服务接口
 *
 * @author ay
 * @since 2023-04-21 16:55:19
 */
public interface IPersonalReportService extends IService<Report> {


    /**
     * 速成或生成检验报告
     *
     * @param param
     * @return
     */
    Boolean create(IReportParam param);


    /**
     * 创建个检健康或职业报告
     *
     * @param param
     * @return
     */
    CreateReportVo createReport(IReportParam param) throws JRException;



    /**
     * 生成复查名单
     * @param ids
     * @param username
     * @return
     */
    void createPdf(String id, String username);

    /**
     * 隐私报告生成
     * @param patientcode
     * @return
     */
    Boolean createTiming(List<String> patientcode);

    /**
     * 生成以前的老系统报告
     * @param param
     * @return
     */
    Boolean createOldReport(IReportParam param);
}

