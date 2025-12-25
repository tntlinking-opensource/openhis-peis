package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.CrmOrderPlan;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 签单计划(CrmOrderPlan)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:44:59
 */
public interface CrmOrderPlanMapper extends BaseMapper<CrmOrderPlan> {

    /**
     * 分页查询[签单计划]列表
     *
     * @param page  分页参数
     * @param param CrmOrderPlan查询参数
     * @return 分页数据
     */
    IPage<CrmOrderPlan> getPage(PageParam<CrmOrderPlan> page, @Param("param") CrmOrderPlan param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmOrderPlan getInfoById(@Param("id") String id);

}
