package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.QxWsUserRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (QxWsUserRole)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:08
 */
public interface QxWsUserRoleMapper extends BaseMapper<QxWsUserRole> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxWsUserRole查询参数
     * @return 分页数据
     */
    IPage<QxWsUserRole> getPage(PageParam<QxWsUserRole> page, @Param("param") QxWsUserRole param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userId
     * @return 详情信息
     */
    QxWsUserRole getInfoById(@Param("id") String id);

}
