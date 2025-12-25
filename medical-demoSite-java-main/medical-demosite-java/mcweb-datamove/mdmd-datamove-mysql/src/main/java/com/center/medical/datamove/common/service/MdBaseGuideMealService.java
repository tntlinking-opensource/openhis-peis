package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdBaseGuideMeal;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 基础推荐套餐(MdBaseGuideMeal)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:09
 */
public interface MdBaseGuideMealService extends IService<MdBaseGuideMeal> {

    /**
     * 分页查询[基础推荐套餐]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdBaseGuideMeal> getPage(PageParam<MdBaseGuideMeal> page, MdBaseGuideMeal param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBaseGuideMeal getInfoById(String id);

}

