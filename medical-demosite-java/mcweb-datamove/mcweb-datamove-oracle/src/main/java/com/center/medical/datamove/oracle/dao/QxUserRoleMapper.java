package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.QxUserRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (QxUserRole)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:23:54
 */
public interface QxUserRoleMapper extends BaseMapper<QxUserRole> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxUserRole查询参数
     * @return 分页数据
     */
    IPage<QxUserRole> getPage(PageParam<QxUserRole> page, @Param("param") QxUserRole param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userId
     * @return 详情信息
     */
    QxUserRole getInfoById(@Param("id") String id);

}
