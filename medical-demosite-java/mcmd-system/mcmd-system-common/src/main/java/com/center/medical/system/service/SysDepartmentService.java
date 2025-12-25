package com.center.medical.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.system.bean.model.SysDepartment;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 沃德医疗部门总集(所有中心部门的总集)(SysDepartment)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-18 19:26:13
 */
public interface SysDepartmentService extends IService<SysDepartment> {

    /**
     * 分页查询[沃德医疗部门总集(所有中心部门的总集)]列表
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

