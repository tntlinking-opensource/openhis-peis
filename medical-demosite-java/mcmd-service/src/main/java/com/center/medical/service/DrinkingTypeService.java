package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.DrinkingType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 饮酒种类(DrinkingType)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:51
 */
public interface DrinkingTypeService extends IService<DrinkingType> {

    /**
     * 分页查询[饮酒种类]列表
     *
     * @param page  分页参数
     * @param param DrinkingType查询参数
     * @return 分页数据
     */
    IPage<DrinkingType> getList(PageParam<DrinkingType> page, DrinkingType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    DrinkingType getInfoById(String id);

}

