package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.DrinkingType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (DrinkingType)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:18:22
 */
public interface DrinkingTypeService extends IService<DrinkingType> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<DrinkingType> getPage(PageParam<DrinkingType> page, DrinkingType param);


}

