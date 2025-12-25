package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.ReportUrl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * BG科室报告目录表(ReportUrl)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:17
 */
public interface ReportUrlMapper extends BaseMapper<ReportUrl> {

    /**
     * 分页查询[BG科室报告目录表]列表
     *
     * @param page  分页参数
     * @param param ReportUrl查询参数
     * @return 分页数据
     */
    IPage<ReportUrl> getPage(PageParam<ReportUrl> page, @Param("param") ReportUrl param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReportUrl getInfoById(@Param("id") String id);

}
