package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Orderandfzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 订单与分中心关联表(Orderandfzx)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:22:25
 */
public interface OrderandfzxService extends IService<Orderandfzx> {

    /**
     * 分页查询[订单与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Orderandfzx> getPage(PageParam<Orderandfzx> page, Orderandfzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Orderandfzx getInfoById(String id);

}

