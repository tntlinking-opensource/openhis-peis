package com.center.medical.report.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.ReportContent;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.param.ReportOpenApiPatientcodeListParam;
import com.center.medical.report.dao.ReportOpenApiMapper;
import com.center.medical.report.service.ReportOpenApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 报告外部接口
 * @author xhp
 * @since 2023-10-24 10:20
 */
@Slf4j
@Service("reportOpenApiService")
@RequiredArgsConstructor
public class ReportOpenApiServiceImpl extends ServiceImpl<ReportOpenApiMapper, ReportContent> implements ReportOpenApiService {
    private final ReportOpenApiMapper reportOpenApiMapper;


    @Override
    public IPage<String> selectPatientcodeList(PageParam page, ReportOpenApiPatientcodeListParam param) {
        return reportOpenApiMapper.selectPatientcodeList(page,param);
    }
}
