package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.CreateOrderQtxz;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 线上变更订单前台须知记录(CreateOrderQtxz)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:17:54
 */
public interface CreateOrderQtxzService extends IService<CreateOrderQtxz> {

    /**
     * 分页查询[线上变更订单前台须知记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CreateOrderQtxz> getPage(PageParam<CreateOrderQtxz> page, CreateOrderQtxz param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CreateOrderQtxz getInfoById(String id);

}

