package com.center.medical.workflow.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.workflow.bean.model.WorkflowType;
import com.center.medical.workflow.dao.WorkflowTypeMapper;
import com.center.medical.workflow.service.WorkflowTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 工作流类型(WorkflowType)服务实现类
 *
 * @author makejava
 * @since 2023-11-08 16:58:12
 */
@Slf4j
@Service("workflowTypeService")
@RequiredArgsConstructor
public class WorkflowTypeServiceImpl extends ServiceImpl<WorkflowTypeMapper, WorkflowType> implements WorkflowTypeService {

    private final WorkflowTypeMapper workflowTypeMapper;

    /**
     * 分页查询[工作流类型]列表
     *
     * @param page  分页参数
     * @param param WorkflowType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WorkflowType> getPage(PageParam<WorkflowType> page, WorkflowType param) {
        return workflowTypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键typeId
     * @return 详情信息
     */
    @Override
    public WorkflowType getInfoById(Integer id) {
        return workflowTypeMapper.getInfoById(id);
    }

}

