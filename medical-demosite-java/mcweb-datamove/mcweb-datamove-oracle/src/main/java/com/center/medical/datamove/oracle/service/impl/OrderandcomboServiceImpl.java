package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.OrderandcomboMapper;
import com.center.medical.datamove.oracle.bean.model.Orderandcombo;
import com.center.medical.datamove.oracle.service.OrderandcomboService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 订单与套餐关联表(Orderandcombo)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:24
 */
@Slf4j
@Service("orderandcomboService")
@RequiredArgsConstructor
public class OrderandcomboServiceImpl extends ServiceImpl<OrderandcomboMapper, Orderandcombo> implements OrderandcomboService {

    private final OrderandcomboMapper orderandcomboMapper;

    /**
     * 分页查询[订单与套餐关联表]列表
     *
     * @param page  分页参数
     * @param param Orderandcombo查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Orderandcombo> getPage(PageParam<Orderandcombo> page, Orderandcombo param) {
        return orderandcomboMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Orderandcombo getInfoById(String id) {
        return orderandcomboMapper.getInfoById(id);
    }

    ;

}


