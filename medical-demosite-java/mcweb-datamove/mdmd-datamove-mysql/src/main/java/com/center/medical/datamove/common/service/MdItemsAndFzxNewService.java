package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdItemsAndFzxNew;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC收费项目和分中心关联表(MdItemsAndFzxNew)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:23
 */
public interface MdItemsAndFzxNewService extends IService<MdItemsAndFzxNew> {

    /**
     * 分页查询[JC收费项目和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdItemsAndFzxNew> getPage(PageParam<MdItemsAndFzxNew> page, MdItemsAndFzxNew param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdItemsAndFzxNew getInfoById(String id);

}

