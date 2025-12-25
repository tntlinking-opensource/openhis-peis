package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.WsUserRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 网站用户权限(WsUserRole)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:43
 */
public interface WsUserRoleMapper extends BaseMapper<WsUserRole> {

    /**
     * 分页查询[网站用户权限]列表
     *
     * @param page  分页参数
     * @param param WsUserRole查询参数
     * @return 分页数据
     */
    IPage<WsUserRole> getPage(PageParam<WsUserRole> page, @Param("param") WsUserRole param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userId
     * @return 详情信息
     */
    WsUserRole getInfoById(@Param("id") String id);

}
