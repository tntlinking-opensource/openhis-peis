package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysVersion1;

/**
 * 自动部署-更新版本信息(SysVersion1)服务接口
 *
 * @author makejava
 * @since 2024-01-23 10:36:17
 */
public interface SysVersion1Service extends IService<SysVersion1> {

    /**
     * 分页查询[自动部署-更新版本信息]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysVersion1> getPage(PageParam<SysVersion1> page, SysVersion1 param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysVersion1 getInfoById(Integer id);

}

