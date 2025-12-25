package com.center.medical.workflow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.workflow.bean.model.WorkflowCase;
import com.center.medical.workflow.bean.model.WorkflowCaseItem;

import java.util.List;

/**
 * 实例节点(WorkflowCaseItem)服务接口
 *
 * @author makejava
 * @since 2023-11-08 16:58:11
 */
public interface WorkflowCaseItemService extends IService<WorkflowCaseItem> {

    /**
     * 分页查询[实例节点]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<WorkflowCaseItem> getPage(PageParam<WorkflowCaseItem> page, WorkflowCaseItem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键itemId
     * @return 详情信息
     */
    WorkflowCaseItem getInfoById(String id);

    /**
     * 根据实例ID获取节点列表
     *
     * @param caseId
     * @return
     */
    List<WorkflowCaseItem> getListByCaseId(String caseId);

    /**
     * 根据当前节点获取下一个节点
     *
     * @param caseItem 当前节点
     * @return
     */
    WorkflowCaseItem getNextItem(WorkflowCaseItem caseItem);

    /**
     * 通知下一级并将下一级状态改为进行中
     *
     * @param caseItem     实例节点
     * @param workflowCase 工作流实例
     */
    void notifyNext(WorkflowCaseItem caseItem, WorkflowCase workflowCase);
}

