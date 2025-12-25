package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.ShortMessageType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC短信信息分类表(ShortMessageType)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:24:59
 */
public interface ShortMessageTypeService extends IService<ShortMessageType> {

    /**
     * 分页查询[JC短信信息分类表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ShortMessageType> getPage(PageParam<ShortMessageType> page, ShortMessageType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ShortMessageType getInfoById(String id);

}

