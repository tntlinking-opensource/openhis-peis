package com.center.medical.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.model.ReportConfig;

/**
 * 报告配置(ReportConfig)服务接口
 *
 * @author ay
 * @since 2023-07-25 10:00:31
 */
public interface ReportConfigService extends IService<ReportConfig> {

    /**
     * 分页查询[报告配置]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ReportConfig> getPage(PageParam<ReportConfig> page, ReportConfig param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReportConfig getInfoById(String id);

    /**
     * 获取分中心报告配置
     *
     * @param branchId
     * @return
     */
    String getBranchConfig(String branchId);
}

