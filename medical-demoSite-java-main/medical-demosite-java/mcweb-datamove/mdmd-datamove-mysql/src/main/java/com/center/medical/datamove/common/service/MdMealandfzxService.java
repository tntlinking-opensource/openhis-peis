package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdMealandfzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 普通套餐与分中心关联表(MdMealandfzx)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:26
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

}

