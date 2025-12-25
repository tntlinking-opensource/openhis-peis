package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.KdDepartment;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 金蝶中的部门信息（kingdeedepartment）(KdDepartment)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:03
 */
public interface KdDepartmentService extends IService<KdDepartment> {

    /**
     * 分页查询[金蝶中的部门信息（kingdeedepartment）]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<KdDepartment> getPage(PageParam<KdDepartment> page, KdDepartment param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键accountNo
     * @return 详情信息
     */
    KdDepartment getInfoById(String id);

}

