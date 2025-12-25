package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.QxRoles;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (QxRoles)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:23:50
 */
public interface QxRolesMapper extends BaseMapper<QxRoles> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxRoles查询参数
     * @return 分页数据
     */
    IPage<QxRoles> getPage(PageParam<QxRoles> page, @Param("param") QxRoles param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    QxRoles getInfoById(@Param("id") String id);

}
