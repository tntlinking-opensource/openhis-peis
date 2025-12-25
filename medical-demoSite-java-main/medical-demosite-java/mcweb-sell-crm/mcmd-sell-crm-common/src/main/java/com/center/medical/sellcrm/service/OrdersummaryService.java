package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Ordersummary;
import com.center.medical.sellcrm.bean.param.OrdersummaryParam;

/**
 * 订单总结表(Ordersummary)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-22 18:38:09
 */
public interface OrdersummaryService extends IService<Ordersummary> {

    /**
     * 分页查询[订单总结表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Ordersummary> getPage(PageParam<Ordersummary> page, OrdersummaryParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Ordersummary getInfoById(String id);

    /**
     * 新增/编辑操作
     *
     * @param ordersummary
     * @return
     */
    Boolean saOrUp(Ordersummary ordersummary);
}

