package com.center.medical.datamove.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.MdComboandfzx;

import java.util.List;

/**
 * 最小套餐与分中心关联表(MdComboandfzx)服务接口
 *
 * @author ay
 * @since 2023-07-25 22:05:51
 */
public interface MdComboandfzxService extends IService<MdComboandfzx> {

    /**
     * 分页查询[最小套餐与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdComboandfzx> getPage(PageParam<MdComboandfzx> page, MdComboandfzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdComboandfzx getInfoById(String id);

    /**
     * 批量插入或更新
     *
     * @param mapAsList
     */
    void saOrUpList(List<MdComboandfzx> mapAsList);
}

