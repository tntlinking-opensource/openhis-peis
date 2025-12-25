package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.SysRoleMenu;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 角色和菜单关联表(SysRoleMenu)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:38
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 分页查询[角色和菜单关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysRoleMenu> getPage(PageParam<SysRoleMenu> page, SysRoleMenu param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键roleId
     * @return 详情信息
     */
    SysRoleMenu getInfoById(Long id);

}

