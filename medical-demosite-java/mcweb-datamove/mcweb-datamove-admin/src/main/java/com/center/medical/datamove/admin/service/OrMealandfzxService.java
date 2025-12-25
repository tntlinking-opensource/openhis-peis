package com.center.medical.datamove.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.Mealandfzx;

import java.util.List;

/**
 * 普通套餐与分中心关联表(Mealandfzx)服务接口
 *
 * @author ay
 * @since 2023-07-25 22:25:31
 */
public interface OrMealandfzxService extends IService<Mealandfzx> {

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
     * 通过套餐id查询
     *
     * @param tcid
     * @return
     */
    List<Mealandfzx> getByTcid(String tcid);
}

