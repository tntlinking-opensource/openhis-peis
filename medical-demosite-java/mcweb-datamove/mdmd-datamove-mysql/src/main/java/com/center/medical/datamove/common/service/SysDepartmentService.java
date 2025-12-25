package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.SysDepartment;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 分中心部门表（对应原系统中的qx_cen_dept）(SysDepartment)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:34
 */
public interface SysDepartmentService extends IService<SysDepartment> {

    /**
     * 分页查询[分中心部门表（对应原系统中的qx_cen_dept）]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysDepartment> getPage(PageParam<SysDepartment> page, SysDepartment param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysDepartment getInfoById(String id);

}

