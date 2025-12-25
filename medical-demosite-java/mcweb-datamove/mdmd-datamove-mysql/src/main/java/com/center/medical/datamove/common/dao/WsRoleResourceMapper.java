package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.WsRoleResource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 角色-资源映射表(WsRoleResource)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:41
 */
public interface WsRoleResourceMapper extends BaseMapper<WsRoleResource> {

    /**
     * 分页查询[角色-资源映射表]列表
     *
     * @param page  分页参数
     * @param param WsRoleResource查询参数
     * @return 分页数据
     */
    IPage<WsRoleResource> getPage(PageParam<WsRoleResource> page, @Param("param") WsRoleResource param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键roleId
     * @return 详情信息
     */
    WsRoleResource getInfoById(@Param("id") String id);

}
