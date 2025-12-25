package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.QxRoleResource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (QxRoleResource)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:23:49
 */
public interface QxRoleResourceMapper extends BaseMapper<QxRoleResource> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxRoleResource查询参数
     * @return 分页数据
     */
    IPage<QxRoleResource> getPage(PageParam<QxRoleResource> page, @Param("param") QxRoleResource param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键roleId
     * @return 详情信息
     */
    QxRoleResource getInfoById(@Param("id") Object id);

}
