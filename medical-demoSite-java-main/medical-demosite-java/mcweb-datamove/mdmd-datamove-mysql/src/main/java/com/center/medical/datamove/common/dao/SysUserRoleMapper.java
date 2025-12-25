package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.SysUserRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 用户和角色关联表(SysUserRole)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:40
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 分页查询[用户和角色关联表]列表
     *
     * @param page  分页参数
     * @param param SysUserRole查询参数
     * @return 分页数据
     */
    IPage<SysUserRole> getPage(PageParam<SysUserRole> page, @Param("param") SysUserRole param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userId
     * @return 详情信息
     */
    SysUserRole getInfoById(@Param("id") Long id);

}
