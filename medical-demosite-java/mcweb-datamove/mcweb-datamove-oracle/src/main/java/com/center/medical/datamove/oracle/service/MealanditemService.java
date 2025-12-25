package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Mealanditem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 普通套餐与收费项目关联表(Mealanditem)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:22:04
 */
public interface MealanditemService extends IService<Mealanditem> {

    /**
     * 分页查询[普通套餐与收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Mealanditem> getPage(PageParam<Mealanditem> page, Mealanditem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Mealanditem getInfoById(String id);

}

