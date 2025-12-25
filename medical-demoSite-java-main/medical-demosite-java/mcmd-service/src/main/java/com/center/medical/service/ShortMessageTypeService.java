package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.ShortMessageType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC短信信息分类表(ShortMessageType)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:55
 */
public interface ShortMessageTypeService extends IService<ShortMessageType> {

    /**
     * 分页查询[JC短信信息分类表]列表
     *
     * @param page  分页参数
     * @param param ShortMessageType查询参数
     * @return 分页数据
     */
    IPage<ShortMessageType> getList(PageParam<ShortMessageType> page, ShortMessageType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    ShortMessageType getInfoById(String id);

}

