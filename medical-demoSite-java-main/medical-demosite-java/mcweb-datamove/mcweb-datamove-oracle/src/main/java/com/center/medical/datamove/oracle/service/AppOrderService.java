package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.AppOrder;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 小程序微信订单(AppOrder)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:11:47
 */
public interface AppOrderService extends IService<AppOrder> {

    /**
     * 分页查询[小程序微信订单]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<AppOrder> getPage(PageParam<AppOrder> page, AppOrder param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppOrder getInfoById(String id);

}

