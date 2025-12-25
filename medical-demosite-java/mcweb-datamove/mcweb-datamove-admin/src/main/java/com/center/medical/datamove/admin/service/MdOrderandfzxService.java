package com.center.medical.datamove.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.MdOrderandfzx;

import java.util.List;

/**
 * 订单与分中心关联表(MdOrderandfzx)服务接口
 *
 * @author ay
 * @since 2023-07-25 21:09:37
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

    /**
     * 批量添加或修改
     *
     * @param mapAsList
     */
    void saOrUpList(List<MdOrderandfzx> mapAsList);
}

