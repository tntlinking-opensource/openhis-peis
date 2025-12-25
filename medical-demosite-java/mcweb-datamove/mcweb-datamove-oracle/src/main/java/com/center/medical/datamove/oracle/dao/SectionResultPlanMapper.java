package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.SectionResultPlan;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (SectionResultPlan)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:43
 */
public interface SectionResultPlanMapper extends BaseMapper<SectionResultPlan> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param SectionResultPlan查询参数
     * @return 分页数据
     */
    IPage<SectionResultPlan> getPage(PageParam<SectionResultPlan> page, @Param("param") SectionResultPlan param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SectionResultPlan getInfoById(@Param("id") String id);

}
