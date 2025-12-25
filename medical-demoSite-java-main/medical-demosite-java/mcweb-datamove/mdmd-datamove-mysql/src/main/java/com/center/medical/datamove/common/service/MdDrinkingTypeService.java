package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdDrinkingType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 饮酒种类(MdDrinkingType)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:13
 */
public interface MdDrinkingTypeService extends IService<MdDrinkingType> {

    /**
     * 分页查询[饮酒种类]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdDrinkingType> getPage(PageParam<MdDrinkingType> page, MdDrinkingType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdDrinkingType getInfoById(String id);

}

