package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.SysDept;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 沃德医疗部门总集(所有中心部门的总集)（对应原系统中的qx_department）(SysDept)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:34
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 分页查询[沃德医疗部门总集(所有中心部门的总集)（对应原系统中的qx_department）]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysDept> getPage(PageParam<SysDept> page, SysDept param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键deptId
     * @return 详情信息
     */
    SysDept getInfoById(Long id);

}

