package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.SysRoleDept;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 角色和部门关联表(SysRoleDept)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:37
 */
public interface SysRoleDeptMapper extends BaseMapper<SysRoleDept> {

    /**
     * 分页查询[角色和部门关联表]列表
     *
     * @param page  分页参数
     * @param param SysRoleDept查询参数
     * @return 分页数据
     */
    IPage<SysRoleDept> getPage(PageParam<SysRoleDept> page, @Param("param") SysRoleDept param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键roleId
     * @return 详情信息
     */
    SysRoleDept getInfoById(@Param("id") Long id);

}
