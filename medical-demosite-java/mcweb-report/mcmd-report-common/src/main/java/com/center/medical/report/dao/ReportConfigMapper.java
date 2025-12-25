package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.model.ReportConfig;
import org.apache.ibatis.annotations.Param;

/**
 * 报告配置(ReportConfig)数据库访问层
 *
 * @author ay
 * @since 2023-07-25 10:00:31
 */
public interface ReportConfigMapper extends BaseMapper<ReportConfig> {

    /**
     * 分页查询[报告配置]列表
     *
     * @param page  分页参数
     * @param param ReportConfig查询参数
     * @return 分页数据
     */
    IPage<ReportConfig> getPage(PageParam<ReportConfig> page, @Param("param") ReportConfig param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReportConfig getInfoById(@Param("id") String id);

    /**
     * 获取分中心报告配置
     *
     * @param branchId
     * @return
     */
    String getBranchConfig(@Param("branchId") String branchId);
}
