package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.QxDepartment;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (QxDepartment)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:23:44
 */
public interface QxDepartmentMapper extends BaseMapper<QxDepartment> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxDepartment查询参数
     * @return 分页数据
     */
    IPage<QxDepartment> getPage(PageParam<QxDepartment> page, @Param("param") QxDepartment param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    QxDepartment getInfoById(@Param("id") String id);

}
