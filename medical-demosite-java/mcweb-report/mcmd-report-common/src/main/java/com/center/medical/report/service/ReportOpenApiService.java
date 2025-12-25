package com.center.medical.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.ReportContent;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.param.ReportOpenApiPatientcodeListParam;

/**
 * 报告外部接口
 * @author xhp
 * @since 2023-10-24 10:20
 */
public interface ReportOpenApiService extends IService<ReportContent> {
    /**
     * 获取体检号列表
     * @param page
     * @param param
     * @return
     */
    IPage<String> selectPatientcodeList(PageParam page, ReportOpenApiPatientcodeListParam param);
}
