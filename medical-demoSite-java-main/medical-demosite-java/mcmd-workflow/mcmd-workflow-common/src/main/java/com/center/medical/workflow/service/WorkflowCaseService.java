package com.center.medical.workflow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.workflow.bean.model.WorkflowCase;
import com.center.medical.workflow.bean.param.UpdateCaseItemParam;
import com.center.medical.workflow.bean.param.WorkflowCaseParam;

import java.util.List;

/**
 * 工作流实例记录(WorkflowCase)服务接口
 *
 * @author makejava
 * @since 2023-11-08 16:58:10
 */
public interface WorkflowCaseService extends IService<WorkflowCase> {

    /**
     * 分页查询[工作流实例记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<WorkflowCase> getPage(PageParam<WorkflowCase> page, WorkflowCaseParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WorkflowCase getInfoById(String id);

    /**
     * 新增或者更新
     *
     * @param workflowCase
     * @return
     */
    Boolean saOrUp(WorkflowCase workflowCase);

    /**
     * 更新实例节点状态
     *
     * @param param 实例节点更新信息
     * @return 修改结果
     */
    Boolean updateItemStatus(UpdateCaseItemParam param);

    /**
     * 工作流实例结束处理
     *
     * @param workflowCase 工作流实例
     */
    void caseOver(WorkflowCase workflowCase);

    /**
     * 根据业务ID获取实例详情
     *
     * @param bizId    业务ID
     * @param typeFlag 类型标识
     * @return 单条数据
     */
    WorkflowCase getInfoByBzId(String bizId, String typeFlag);


    /**
     * 更新审批流状态
     *
     * @param bizIds   业务ID集合
     * @param typeFlag 工作流类型标识
     * @param status   更新状态
     * @param reason   更新原因
     * @return
     */
    Boolean loseByBizId(List<String> bizIds, String typeFlag, Integer status, String reason);
}

