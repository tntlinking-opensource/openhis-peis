package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdShortMessageType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC短信信息分类表(MdShortMessageType)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:41
 */
public interface MdShortMessageTypeService extends IService<MdShortMessageType> {

    /**
     * 分页查询[JC短信信息分类表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdShortMessageType> getPage(PageParam<MdShortMessageType> page, MdShortMessageType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdShortMessageType getInfoById(String id);

}

