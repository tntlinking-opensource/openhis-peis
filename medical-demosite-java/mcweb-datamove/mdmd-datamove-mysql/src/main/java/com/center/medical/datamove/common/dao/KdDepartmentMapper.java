package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.KdDepartment;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 金蝶中的部门信息（kingdeedepartment）(KdDepartment)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:03
 */
public interface KdDepartmentMapper extends BaseMapper<KdDepartment> {

    /**
     * 分页查询[金蝶中的部门信息（kingdeedepartment）]列表
     *
     * @param page  分页参数
     * @param param KdDepartment查询参数
     * @return 分页数据
     */
    IPage<KdDepartment> getPage(PageParam<KdDepartment> page, @Param("param") KdDepartment param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键accountNo
     * @return 详情信息
     */
    KdDepartment getInfoById(@Param("id") String id);

}
