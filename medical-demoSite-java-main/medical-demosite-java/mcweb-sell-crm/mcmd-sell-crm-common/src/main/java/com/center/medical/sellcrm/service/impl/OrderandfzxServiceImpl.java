package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Orderandfzx;
import com.center.medical.sellcrm.dao.OrderandfzxMapper;
import com.center.medical.sellcrm.service.OrderandfzxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单与分中心关联表(Orderandfzx)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-22 18:38:09
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


    /**
     * 通过订单id查询关联的分中心名称
     * @param ddid
     * @return
     */
    @Override
    public List<String> getBranchNameByDdid(String ddid) {
        return orderandfzxMapper.getBranchNameByDdid(ddid);
    }
}

