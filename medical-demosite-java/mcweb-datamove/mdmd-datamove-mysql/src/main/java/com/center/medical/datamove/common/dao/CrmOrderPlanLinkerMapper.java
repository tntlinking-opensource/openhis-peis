package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.CrmOrderPlanLinker;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 签单计划联系人(CrmOrderPlanLinker)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:44:59
 */
public interface CrmOrderPlanLinkerMapper extends BaseMapper<CrmOrderPlanLinker> {

    /**
     * 分页查询[签单计划联系人]列表
     *
     * @param page  分页参数
     * @param param CrmOrderPlanLinker查询参数
     * @return 分页数据
     */
    IPage<CrmOrderPlanLinker> getPage(PageParam<CrmOrderPlanLinker> page, @Param("param") CrmOrderPlanLinker param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmOrderPlanLinker getInfoById(@Param("id") String id);

}
