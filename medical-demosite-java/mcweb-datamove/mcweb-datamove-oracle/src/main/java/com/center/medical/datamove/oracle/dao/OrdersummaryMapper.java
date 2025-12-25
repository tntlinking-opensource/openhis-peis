package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Ordersummary;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 订单总结表(Ordersummary)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:26
 */
public interface OrdersummaryMapper extends BaseMapper<Ordersummary> {

    /**
     * 分页查询[订单总结表]列表
     *
     * @param page  分页参数
     * @param param Ordersummary查询参数
     * @return 分页数据
     */
    IPage<Ordersummary> getPage(PageParam<Ordersummary> page, @Param("param") Ordersummary param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Ordersummary getInfoById(@Param("id") String id);

}
