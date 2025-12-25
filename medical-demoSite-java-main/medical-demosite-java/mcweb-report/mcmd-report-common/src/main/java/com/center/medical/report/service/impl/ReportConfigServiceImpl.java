package com.center.medical.report.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.model.ReportConfig;
import com.center.medical.report.dao.ReportConfigMapper;
import com.center.medical.report.service.ReportConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 报告配置(ReportConfig)服务实现类
 *
 * @author ay
 * @since 2023-07-25 10:00:31
 */
@Slf4j
@Service("reportConfigService")
@RequiredArgsConstructor
public class ReportConfigServiceImpl extends ServiceImpl<ReportConfigMapper, ReportConfig> implements ReportConfigService {

    private final ReportConfigMapper reportConfigMapper;

    /**
     * 分页查询[报告配置]列表
     *
     * @param page  分页参数
     * @param param ReportConfig查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ReportConfig> getPage(PageParam<ReportConfig> page, ReportConfig param) {
        return reportConfigMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ReportConfig getInfoById(String id) {
        return reportConfigMapper.getInfoById(id);
    }


    /**
     * 获取分中心报告配置
     *
     * @param branchId
     * @return
     */
    @Override
    public String getBranchConfig(String branchId) {
        return reportConfigMapper.getBranchConfig(branchId);
    }
}


