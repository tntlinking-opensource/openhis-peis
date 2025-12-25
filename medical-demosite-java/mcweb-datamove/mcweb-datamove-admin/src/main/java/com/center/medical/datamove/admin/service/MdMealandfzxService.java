package com.center.medical.datamove.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.MdMealandfzx;

import java.util.List;

/**
 * 普通套餐与分中心关联表(MdMealandfzx)服务接口
 *
 * @author ay
 * @since 2023-07-25 22:26:22
 */
public interface MdMealandfzxService extends IService<MdMealandfzx> {

    /**
     * 分页查询[普通套餐与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdMealandfzx> getPage(PageParam<MdMealandfzx> page, MdMealandfzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdMealandfzx getInfoById(String id);

    /**
     * 批量插入或更新
     *
     * @param mapAsList
     */
    void saOrUpList(List<MdMealandfzx> mapAsList);
}

