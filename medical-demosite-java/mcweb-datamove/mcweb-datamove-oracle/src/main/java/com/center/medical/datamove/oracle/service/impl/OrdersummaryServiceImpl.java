package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.OrdersummaryMapper;
import com.center.medical.datamove.oracle.bean.model.Ordersummary;
import com.center.medical.datamove.oracle.service.OrdersummaryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 订单总结表(Ordersummary)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:27
 */
@Slf4j
@Service("ordersummaryService")
@RequiredArgsConstructor
public class OrdersummaryServiceImpl extends ServiceImpl<OrdersummaryMapper, Ordersummary> implements OrdersummaryService {

    private final OrdersummaryMapper ordersummaryMapper;

    /**
     * 分页查询[订单总结表]列表
     *
     * @param page  分页参数
     * @param param Ordersummary查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Ordersummary> getPage(PageParam<Ordersummary> page, Ordersummary param) {
        return ordersummaryMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Ordersummary getInfoById(String id) {
        return ordersummaryMapper.getInfoById(id);
    }

}


