package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.QxWsRoleResource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (QxWsRoleResource)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:05
 */
public interface QxWsRoleResourceMapper extends BaseMapper<QxWsRoleResource> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxWsRoleResource查询参数
     * @return 分页数据
     */
    IPage<QxWsRoleResource> getPage(PageParam<QxWsRoleResource> page, @Param("param") QxWsRoleResource param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键roleId
     * @return 详情信息
     */
    QxWsRoleResource getInfoById(@Param("id") Object id);

}
