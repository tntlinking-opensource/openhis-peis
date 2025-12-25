package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdSectionResultPlan;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 科室批量录入结果(MdSectionResultPlan)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:34
 */
public interface MdSectionResultPlanMapper extends BaseMapper<MdSectionResultPlan> {

    /**
     * 分页查询[科室批量录入结果]列表
     *
     * @param page  分页参数
     * @param param MdSectionResultPlan查询参数
     * @return 分页数据
     */
    IPage<MdSectionResultPlan> getPage(PageParam<MdSectionResultPlan> page, @Param("param") MdSectionResultPlan param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSectionResultPlan getInfoById(@Param("id") String id);

}
