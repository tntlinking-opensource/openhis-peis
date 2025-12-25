package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.SysRoleMenu;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 角色和菜单关联表(SysRoleMenu)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:38
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 分页查询[角色和菜单关联表]列表
     *
     * @param page  分页参数
     * @param param SysRoleMenu查询参数
     * @return 分页数据
     */
    IPage<SysRoleMenu> getPage(PageParam<SysRoleMenu> page, @Param("param") SysRoleMenu param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键roleId
     * @return 详情信息
     */
    SysRoleMenu getInfoById(@Param("id") Long id);

}
