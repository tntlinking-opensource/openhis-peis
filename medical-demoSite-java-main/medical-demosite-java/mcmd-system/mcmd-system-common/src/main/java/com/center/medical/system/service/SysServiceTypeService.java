package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysServiceType;

/**
 * 系统服务(记录系统服务种类)(SysServiceType)服务接口
 *
 * @author makejava
 * @since 2024-03-01 18:02:35
 */
public interface SysServiceTypeService extends IService<SysServiceType> {

    /**
     * 分页查询[系统服务(记录系统服务种类)]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysServiceType> getPage(PageParam<SysServiceType> page, SysServiceType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键serviceId
     * @return 详情信息
     */
    SysServiceType getInfoById(Integer id);

}

