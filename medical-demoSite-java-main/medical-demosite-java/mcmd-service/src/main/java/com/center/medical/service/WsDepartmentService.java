package com.center.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.WsDepartment;
import com.center.medical.common.utils.page.PageParam;

/**
 * 网站部门(WsDepartment)服务接口
 *
 * @author ay
 * @since 2024-05-29 16:36:15
 */
public interface WsDepartmentService extends IService<WsDepartment> {

    /**
     * 分页查询[网站部门]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<WsDepartment> getPage(PageParam<WsDepartment> page, WsDepartment param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WsDepartment getInfoById(String id);

}

