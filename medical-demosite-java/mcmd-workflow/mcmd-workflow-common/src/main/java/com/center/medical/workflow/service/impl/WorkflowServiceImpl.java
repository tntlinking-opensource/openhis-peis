package com.center.medical.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.workflow.bean.model.Workflow;
import com.center.medical.workflow.bean.model.WorkflowItem;
import com.center.medical.workflow.bean.param.WorkflowParam;
import com.center.medical.workflow.bean.vo.WorkflowVo;
import com.center.medical.workflow.dao.WorkflowMapper;
import com.center.medical.workflow.service.WorkflowItemService;
import com.center.medical.workflow.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 工作流(Workflow)服务实现类
 *
 * @author makejava
 * @since 2023-11-08 16:58:10
 */
@Slf4j
@Service("workflowService")
@RequiredArgsConstructor
public class WorkflowServiceImpl extends ServiceImpl<WorkflowMapper, Workflow> implements WorkflowService {

    private final WorkflowMapper workflowMapper;
    private final WorkflowItemService workflowItemService;

    /**
     * 分页查询[工作流]列表
     *
     * @param page  分页参数
     * @param param Workflow查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WorkflowVo> getPage(PageParam<Workflow> page, WorkflowParam param) {
        return workflowMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Workflow getInfoById(String id) {
        Workflow workflow = workflowMapper.getInfoById(id);
        workflow.setItemList(workflowItemService.getListByFlowId(id));
        return workflow;
    }

    /**
     * 新增或者更新
     *
     * @param workflow
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(Workflow workflow) {

        if (StringUtils.isBlank(workflow.getId())) {
            //新增
            //判断是否重复：一个分中心只能有一条status=1的typeFlag记录
            if (workflow.getStatus() == 1) {
                Long count = workflowMapper.selectCount(new LambdaQueryWrapper<Workflow>().eq(Workflow::getFzxid, workflow.getFzxid()).eq(Workflow::getStatus, 1).eq(Workflow::getTypeFlag, workflow.getTypeFlag()));
                if (count > 0) {
                    throw new ServiceException("该中心已启用了相同的工作流！");
                }
            }

            //1、增加记录
            workflow.setCreator(SecurityUtils.getUserNo());
            workflowMapper.insert(workflow);

            //2、新增节点列表
            for (WorkflowItem item : workflow.getItemList()) {
                item.setFlowId(workflow.getId());
            }
            workflowItemService.saveBatch(workflow.getItemList());

        } else {
            //更新
            //判断是否重复：一个分中心只能有一条status=1的typeFlag记录
            if (workflow.getStatus() == 1) {
                Long count = workflowMapper.selectCount(new LambdaQueryWrapper<Workflow>().eq(Workflow::getFzxid, workflow.getFzxid())
                        .eq(Workflow::getTypeFlag, workflow.getTypeFlag()).ne(Workflow::getId, workflow.getId()));
                if (count > 0) {
                    throw new ServiceException("该中心已启用了相同的工作流！");
                }
            }

            // 1、更新记录
            workflow.setModifier(SecurityUtils.getUserNo());
            workflowMapper.updateById(workflow);

            // 2、删除原有的节点
            workflowItemService.remove(new LambdaQueryWrapper<WorkflowItem>().eq(WorkflowItem::getFlowId, workflow.getId()));
            //2、新增节点列表
            for (WorkflowItem item : workflow.getItemList()) {
                item.setFlowId(workflow.getId());
            }
            // 3、添加新的节点
            workflowItemService.saveBatch(workflow.getItemList());

        }
        return Boolean.TRUE;
    }

    /**
     * 删除工作流
     *
     * @param ids 工作流ID集合
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delByIds(List<String> ids) {
        //删除工作流
        workflowMapper.deleteBatchIds(ids);

        //删除工作流的节点
        workflowItemService.remove(new LambdaQueryWrapper<WorkflowItem>().in(WorkflowItem::getFlowId, ids));

        return Boolean.TRUE;
    }

    /**
     * 判断是否开启工作流
     *
     * @param cId      分中心ID
     * @param typeFlag 工作流类型标识
     * @return
     */
    @Override
    public Workflow isOpen(String cId, String typeFlag) {
        List<Workflow> workflows = workflowMapper.selectList(new LambdaQueryWrapper<Workflow>().eq(Workflow::getFzxid, cId)
                .eq(Workflow::getTypeFlag, typeFlag).eq(Workflow::getStatus, 1));
        if (workflows.size() > 1) {
            throw new ServiceException("操作失败，系统开启了多个工作流，请联系管理员解决!");
        }
        if (workflows.size() == 1) {
            return workflows.get(0);
        }
        return null;
    }

}

