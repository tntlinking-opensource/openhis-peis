package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Report;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * BG报告主表(Report)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:16
 */
public interface ReportMapper extends BaseMapper<Report> {

    /**
     * 分页查询[BG报告主表]列表
     *
     * @param page  分页参数
     * @param param Report查询参数
     * @return 分页数据
     */
    IPage<Report> getPage(PageParam<Report> page, @Param("param") Report param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Report getInfoById(@Param("id") String id);

}
