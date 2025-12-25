package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdDevicetypePositionCheckitem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 东软pacs部位方法基础表(MdDevicetypePositionCheckitem)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:21
 */
public interface MdDevicetypePositionCheckitemService extends IService<MdDevicetypePositionCheckitem> {

    /**
     * 分页查询[东软pacs部位方法基础表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdDevicetypePositionCheckitem> getPage(PageParam<MdDevicetypePositionCheckitem> page, MdDevicetypePositionCheckitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdDevicetypePositionCheckitem getInfoById(String id);

}

