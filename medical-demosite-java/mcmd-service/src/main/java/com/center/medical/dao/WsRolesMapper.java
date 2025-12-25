package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.WsRoles;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 网站角色(WsRoles)数据库访问层
 *
 * @author ay
 * @since 2024-05-29 16:24:12
 */
public interface WsRolesMapper extends BaseMapper<WsRoles> {

    /**
     * 分页查询[网站角色]列表
     *
     * @param page  分页参数
     * @param param WsRoles查询参数
     * @return 分页数据
     */
    IPage<WsRoles> getPage(PageParam<WsRoles> page, @Param("param") WsRoles param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WsRoles getInfoById(@Param("id") String id);

}
