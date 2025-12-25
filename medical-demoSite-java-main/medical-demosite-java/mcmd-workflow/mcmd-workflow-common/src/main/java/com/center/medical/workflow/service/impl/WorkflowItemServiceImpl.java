package com.center.medical.workflow.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.workflow.bean.model.WorkflowItem;
import com.center.medical.workflow.dao.WorkflowItemMapper;
import com.center.medical.workflow.service.WorkflowItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 工作流节点(WorkflowItem)服务实现类
 *
 * @author makejava
 * @since 2023-11-08 16:58:12
 */
@Slf4j
@Service("workflowItemService")
@RequiredArgsConstructor
public class WorkflowItemServiceImpl extends ServiceImpl<WorkflowItemMapper, WorkflowItem> implements WorkflowItemService {

    private final WorkflowItemMapper workflowItemMapper;

    /**
     * 分页查询[工作流节点]列表
     *
     * @param page  分页参数
     * @param param WorkflowItem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WorkflowItem> getPage(PageParam<WorkflowItem> page, WorkflowItem param) {
        return workflowItemMapper.getPage(page, param);
    }

    /**
     * 根据工作流ID获取节点列表
     *
     * @param flowId
     * @return
     */
    @Override
    public List<WorkflowItem> getListByFlowId(String flowId) {
        return workflowItemMapper.getListByFlowId(flowId);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键itemId
     * @return 详情信息
     */
    @Override
    public WorkflowItem getInfoById(String id) {
        return workflowItemMapper.getInfoById(id);
    }

}

