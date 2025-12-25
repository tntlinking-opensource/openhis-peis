package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.OrderPlan;
import com.center.medical.sellcrm.bean.param.OrderPlanParam;
import org.apache.ibatis.annotations.Param;

/**
 * 签单计划(OrderPlan)表数据库访问层
 *
 * @author 路飞船长
 * @since 2023-05-16 16:47:56
 */
public interface OrderPlanMapper extends BaseMapper<OrderPlan> {

    /**
     * 分页查询[签单计划]列表
     *
     * @param page  分页参数
     * @param param OrderPlan查询参数
     * @return 分页数据
     */
    IPage<OrderPlan> getPage(PageParam<OrderPlan> page, @Param("param") OrderPlanParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrderPlan getInfoById(@Param("id") String id);

}
