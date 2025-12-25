package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Ordersummary;
import com.center.medical.sellcrm.bean.param.OrdersummaryParam;
import org.apache.ibatis.annotations.Param;

/**
 * 订单总结表(Ordersummary)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-22 18:38:09
 */
public interface OrdersummaryMapper extends BaseMapper<Ordersummary> {

    /**
     * 分页查询[订单总结表]列表
     *
     * @param page  分页参数
     * @param param Ordersummary查询参数
     * @return 分页数据
     */
    IPage<Ordersummary> getPage(PageParam<Ordersummary> page, @Param("param") OrdersummaryParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Ordersummary getInfoById(@Param("id") String id);

}
