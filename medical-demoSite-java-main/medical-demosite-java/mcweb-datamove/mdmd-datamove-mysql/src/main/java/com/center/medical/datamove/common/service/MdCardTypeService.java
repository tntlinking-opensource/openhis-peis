package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdCardType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 卡类型(MdCardType)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:15
 */
public interface MdCardTypeService extends IService<MdCardType> {

    /**
     * 分页查询[卡类型]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdCardType> getPage(PageParam<MdCardType> page, MdCardType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCardType getInfoById(String id);

}

