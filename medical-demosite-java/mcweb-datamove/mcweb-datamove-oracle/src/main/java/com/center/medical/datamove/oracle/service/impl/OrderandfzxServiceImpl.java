package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.OrderandfzxMapper;
import com.center.medical.datamove.oracle.bean.model.Orderandfzx;
import com.center.medical.datamove.oracle.service.OrderandfzxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 订单与分中心关联表(Orderandfzx)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:26
 */
@Slf4j
@Service("orderandfzxService")
@RequiredArgsConstructor
public class OrderandfzxServiceImpl extends ServiceImpl<OrderandfzxMapper, Orderandfzx> implements OrderandfzxService {

    private final OrderandfzxMapper orderandfzxMapper;

    /**
     * 分页查询[订单与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param Orderandfzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Orderandfzx> getPage(PageParam<Orderandfzx> page, Orderandfzx param) {
        return orderandfzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Orderandfzx getInfoById(String id) {
        return orderandfzxMapper.getInfoById(id);
    }

}


