package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysVersionBranch1;

/**
 * 自动部署-版本更新分中心关联表(SysVersionBranch1)服务接口
 *
 * @author makejava
 * @since 2024-01-23 10:36:17
 */
public interface SysVersionBranch1Service extends IService<SysVersionBranch1> {

    /**
     * 分页查询[自动部署-版本更新分中心关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysVersionBranch1> getPage(PageParam<SysVersionBranch1> page, SysVersionBranch1 param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysVersionBranch1 getInfoById(Integer id);

}

