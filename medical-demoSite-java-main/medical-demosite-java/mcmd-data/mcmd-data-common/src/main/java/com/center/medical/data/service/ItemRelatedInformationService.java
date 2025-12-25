package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ItemRelatedInformation;

/**
 * 第三方接口关联记录(ItemRelatedInformation)表服务接口
 *
 * @author ay
 * @since 2023-04-07 17:20:14
 */
public interface ItemRelatedInformationService extends IService<ItemRelatedInformation> {

    /**
     * 分页查询[第三方接口关联记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ItemRelatedInformation> getList(PageParam<ItemRelatedInformation> page, ItemRelatedInformation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ItemRelatedInformation getInfoById(String id);

}

