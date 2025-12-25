package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.CreateOrderQtxzMapper;
import com.center.medical.bean.model.CreateOrderQtxz;
import com.center.medical.service.CreateOrderQtxzService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 线上变更订单前台须知记录(CreateOrderQtxz)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:24
 */
@Slf4j
@Service("createOrderQtxzService")
@RequiredArgsConstructor
public class CreateOrderQtxzServiceImpl extends ServiceImpl<CreateOrderQtxzMapper, CreateOrderQtxz> implements CreateOrderQtxzService {

    private final CreateOrderQtxzMapper createOrderQtxzMapper;

    /**
     * 分页查询[线上变更订单前台须知记录]列表
     *
     * @param page  分页参数
     * @param param CreateOrderQtxz查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CreateOrderQtxz> getList(PageParam<CreateOrderQtxz> page, CreateOrderQtxz param) {
        return createOrderQtxzMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public CreateOrderQtxz getInfoById(String id) {
        return createOrderQtxzMapper.getInfoById(id);
    }

}

