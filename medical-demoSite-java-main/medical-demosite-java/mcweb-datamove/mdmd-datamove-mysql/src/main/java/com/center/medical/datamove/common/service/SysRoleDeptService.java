package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.SysRoleDept;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 角色和部门关联表(SysRoleDept)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:38
 */
public interface SysRoleDeptService extends IService<SysRoleDept> {

    /**
     * 分页查询[角色和部门关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysRoleDept> getPage(PageParam<SysRoleDept> page, SysRoleDept param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键roleId
     * @return 详情信息
     */
    SysRoleDept getInfoById(Long id);

}

