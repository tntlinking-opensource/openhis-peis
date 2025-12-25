package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.sellcrm.bean.model.OrderPlanLinker;
import com.center.medical.sellcrm.dao.OrderPlanLinkerMapper;
import com.center.medical.sellcrm.service.OrderPlanLinkerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 签单计划联系人(OrderPlanLinker)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-04-28 11:36:51
 */
@Slf4j
@Service("orderPlanLinkerService")
@RequiredArgsConstructor
public class OrderPlanLinkerServiceImpl extends ServiceImpl<OrderPlanLinkerMapper, OrderPlanLinker> implements OrderPlanLinkerService {

    private final OrderPlanLinkerMapper orderPlanLinkerMapper;

    /**
     * 获取列表
     *
     * @param planId 签单计划ID
     * @return 所有数据
     */
    @Override
    public List<OrderPlanLinker> getList(String planId) {
        return orderPlanLinkerMapper.getList(planId);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrderPlanLinker getInfoById(String id) {
        return orderPlanLinkerMapper.getInfoById(id);
    }

}

