package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.SysUserRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 用户和角色关联表(SysUserRole)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:40
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 分页查询[用户和角色关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysUserRole> getPage(PageParam<SysUserRole> page, SysUserRole param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userId
     * @return 详情信息
     */
    SysUserRole getInfoById(Long id);

}

