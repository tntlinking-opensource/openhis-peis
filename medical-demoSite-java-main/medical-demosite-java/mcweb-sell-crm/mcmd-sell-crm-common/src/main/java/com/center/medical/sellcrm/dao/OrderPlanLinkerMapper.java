package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.sellcrm.bean.model.OrderPlanLinker;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 签单计划联系人(OrderPlanLinker)表数据库访问层
 *
 * @author 路飞船长
 * @since 2023-04-28 11:36:51
 */
public interface OrderPlanLinkerMapper extends BaseMapper<OrderPlanLinker> {

    /**
     * 获取列表
     *
     * @param planId 签单计划ID
     * @return 所有数据
     */
    List<OrderPlanLinker> getList(@Param("planId") String planId);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrderPlanLinker getInfoById(@Param("id") String id);

}
