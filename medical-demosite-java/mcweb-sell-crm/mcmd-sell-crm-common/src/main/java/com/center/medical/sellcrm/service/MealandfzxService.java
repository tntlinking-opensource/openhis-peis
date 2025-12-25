package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Mealandfzx;

/**
 * 普通套餐与分中心关联表(Mealandfzx)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-21 19:41:48
 */
public interface MealandfzxService extends IService<Mealandfzx> {

    /**
     * 分页查询[普通套餐与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Mealandfzx> getPage(PageParam<Mealandfzx> page, Mealandfzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Mealandfzx getInfoById(String id);

    /**
     * 根据主键id和分中心id查询记录条数
     * @param id
     * @param fzxIds
     * @return
     */
    int countByIdAndFzx(String id, String[] fzxIds);
}

