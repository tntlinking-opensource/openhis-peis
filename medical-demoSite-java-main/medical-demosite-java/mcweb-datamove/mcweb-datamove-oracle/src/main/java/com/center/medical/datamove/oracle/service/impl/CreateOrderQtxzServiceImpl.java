package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.CreateOrderQtxzMapper;
import com.center.medical.datamove.oracle.bean.model.CreateOrderQtxz;
import com.center.medical.datamove.oracle.service.CreateOrderQtxzService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 线上变更订单前台须知记录(CreateOrderQtxz)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:17:54
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
    public IPage<CreateOrderQtxz> getPage(PageParam<CreateOrderQtxz> page, CreateOrderQtxz param) {
        return createOrderQtxzMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CreateOrderQtxz getInfoById(String id) {
        return createOrderQtxzMapper.getInfoById(id);
    }

}


