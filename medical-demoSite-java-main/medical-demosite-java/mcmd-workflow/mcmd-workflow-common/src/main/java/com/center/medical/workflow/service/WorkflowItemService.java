package com.center.medical.workflow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.workflow.bean.model.WorkflowItem;

import java.util.List;

/**
 * 工作流节点(WorkflowItem)服务接口
 *
 * @author makejava
 * @since 2023-11-08 16:58:12
 */
public interface WorkflowItemService extends IService<WorkflowItem> {

    /**
     * 分页查询[工作流节点]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<WorkflowItem> getPage(PageParam<WorkflowItem> page, WorkflowItem param);

    /**
     * 根据工作流ID获取节点列表
     *
     * @param flowId
     * @return
     */
    List<WorkflowItem> getListByFlowId(String flowId);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键itemId
     * @return 详情信息
     */
    WorkflowItem getInfoById(String id);

}

