package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.ItemRelatedInformation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 第三方项目接口对接信息表(ItemRelatedInformation)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:19:19
 */
public interface ItemRelatedInformationService extends IService<ItemRelatedInformation> {

    /**
     * 分页查询[第三方项目接口对接信息表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ItemRelatedInformation> getPage(PageParam<ItemRelatedInformation> page, ItemRelatedInformation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ItemRelatedInformation getInfoById(String id);

}

