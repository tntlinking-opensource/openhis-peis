package com.center.medical.report.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Report;
import com.center.medical.report.bean.vo.createReviewVo;

/**
 * BG报告主表(Report)表服务接口
 *
 * @author ay
 * @since 2023-04-26 17:43:49
 */
public interface ReviewNoticeService extends IService<Report> {


    /**
     * 查询复查通知单
     * @param patientno
     * @return
     */
    createReviewVo createReview(String patientno,String idPatientclass);
}

