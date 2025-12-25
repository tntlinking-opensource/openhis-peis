package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.KdDepartment;
import com.center.medical.system.bean.param.KdDepartmentParam;
import org.apache.ibatis.annotations.Param;

/**
 * kingdeedepartment(KdDepartment)表数据库访问层
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:42
 */
public interface KdDepartmentMapper extends BaseMapper<KdDepartment> {

    /**
     * 分页查询[kingdeedepartment]列表
     *
     * @param page  分页参数
     * @param param KdDepartment查询参数
     * @return 分页数据
     */
    IPage<KdDepartment> getPage(PageParam<KdDepartment> page, @Param("param") KdDepartmentParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键accountNo
     * @return 详情信息
     */
    KdDepartment getInfoById(@Param("id") String id);

}
