package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysServiceBranch;

/**
 * 系统服务-分中心关联记录(SysServiceBranch)服务接口
 *
 * @author makejava
 * @since 2024-03-01 18:02:35
 */
public interface SysServiceBranchService extends IService<SysServiceBranch> {

    /**
     * 分页查询[系统服务-分中心关联记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysServiceBranch> getPage(PageParam<SysServiceBranch> page, SysServiceBranch param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysServiceBranch getInfoById(Integer id);

}

