package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Ordersummary;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 订单总结表(Ordersummary)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:22:27
 */
public interface OrdersummaryService extends IService<Ordersummary> {

    /**
     * 分页查询[订单总结表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Ordersummary> getPage(PageParam<Ordersummary> page, Ordersummary param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Ordersummary getInfoById(String id);

}

