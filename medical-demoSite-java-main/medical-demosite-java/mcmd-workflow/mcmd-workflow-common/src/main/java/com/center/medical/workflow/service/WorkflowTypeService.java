package com.center.medical.workflow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.workflow.bean.model.WorkflowType;

/**
 * 工作流类型(WorkflowType)服务接口
 *
 * @author makejava
 * @since 2023-11-08 16:58:12
 */
public interface WorkflowTypeService extends IService<WorkflowType> {

    /**
     * 分页查询[工作流类型]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<WorkflowType> getPage(PageParam<WorkflowType> page, WorkflowType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键typeId
     * @return 详情信息
     */
    WorkflowType getInfoById(Integer id);

}

