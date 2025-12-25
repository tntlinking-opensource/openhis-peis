package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.ReportContent;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.param.ReportOpenApiPatientcodeListParam;
import org.apache.ibatis.annotations.Param;

/**
 * 报告外部接口
 * @author xhp
 * @since 2023-10-24 10:21
 */
public interface ReportOpenApiMapper extends BaseMapper<ReportContent> {
    /**
     * 获取体检号列表
     * @param page
     * @param param
     * @return
     */
    IPage<String> selectPatientcodeList(PageParam page, @Param("param") ReportOpenApiPatientcodeListParam param);


}
