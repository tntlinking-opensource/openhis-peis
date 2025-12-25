package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdOrderandfzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 订单与分中心关联表(MdOrderandfzx)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:30
 */
public interface MdOrderandfzxService extends IService<MdOrderandfzx> {

    /**
     * 分页查询[订单与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdOrderandfzx> getPage(PageParam<MdOrderandfzx> page, MdOrderandfzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdOrderandfzx getInfoById(String id);

}

