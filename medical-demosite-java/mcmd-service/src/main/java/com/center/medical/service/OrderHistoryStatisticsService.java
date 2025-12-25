package com.center.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.OrderHistoryStatistics;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.utils.page.PageParam;

/**
 * 订单历史平均折扣、变动成本率统计表(OrderHistoryStatistics)服务接口
 *
 * @author ay
 * @since 2023-12-07 14:05:37
 */
public interface OrderHistoryStatisticsService extends IService<OrderHistoryStatistics> {

    /**
     * 分页查询[订单历史平均折扣、变动成本率统计表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrderHistoryStatistics> getPage(PageParam<OrderHistoryStatistics> page, OrderHistoryStatistics param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrderHistoryStatistics getInfoById(String id);

    /**
     * 审核时展示订单平均变动成本率以及历史变动成本率
     * @param idOrg
     * @return
     */
    String getOrderHistoryStatisticsStr(String idOrg);

    /**
     * 插入订单历史折扣成本率等
     * @param param
     * @return
     */
    Boolean insertOrderHistoryStatistics(BaseParam param);
}

