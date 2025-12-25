package com.center.medical.workflow.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.event.ExcessReservationEvent;
import com.center.medical.bean.event.OrderFlowEvent;
import com.center.medical.bean.event.OverReservationEvent;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.bean.param.ExcessReservationParam;
import com.center.medical.bean.param.OrderFlowParam;
import com.center.medical.bean.param.OverReservationParam;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.workflow.bean.enums.WorkflowType;
import com.center.medical.workflow.bean.event.MaterialReviewEvent;
import com.center.medical.workflow.bean.model.WorkflowCase;
import com.center.medical.workflow.bean.model.WorkflowCaseItem;
import com.center.medical.workflow.bean.model.WorkflowItem;
import com.center.medical.workflow.bean.param.MaterialReviewParam;
import com.center.medical.workflow.bean.param.UpdateCaseItemParam;
import com.center.medical.workflow.bean.param.WorkflowCaseParam;
import com.center.medical.workflow.dao.WorkflowCaseMapper;
import com.center.medical.workflow.dao.WorkflowReservationTypeMapper;
import com.center.medical.workflow.service.WorkflowCaseItemService;
import com.center.medical.workflow.service.WorkflowCaseService;
import com.center.medical.workflow.service.WorkflowItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 工作流实例记录(WorkflowCase)服务实现类
 *
 * @author makejava
 * @since 2023-11-08 16:58:11
 */
@Slf4j
@Service("workflowCaseService")
@RequiredArgsConstructor
public class WorkflowCaseServiceImpl extends ServiceImpl<WorkflowCaseMapper, WorkflowCase> implements WorkflowCaseService {

    private final WorkflowCaseMapper workflowCaseMapper;
    private final WorkflowCaseItemService workflowCaseItemService;
    private final WorkflowItemService workflowItemService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ISysBranchService iSysBranchService;
    private final WorkflowReservationTypeMapper workflowReservationTypeMapper;



    /**
     * 分页查询[工作流实例记录]列表
     *
     * @param page  分页参数
     * @param param WorkflowCase查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WorkflowCase> getPage(PageParam<WorkflowCase> page, WorkflowCaseParam param) {
        param.setUserNo(SecurityUtils.getUserNo());
        return workflowCaseMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public WorkflowCase getInfoById(String id) {
        WorkflowCase workflowCase = workflowCaseMapper.getInfoById(id);
        workflowCase.setItemList(workflowCaseItemService.getListByCaseId(id));
        workflowCase.setReservationTypeList(workflowReservationTypeMapper.getListByCaseId(id));
        if (StringUtils.isNotEmpty(workflowCase.getData())){
            workflowCase.setDataObject(JSON.parseObject(workflowCase.getData()));
        }
        return workflowCase;
    }

    /**
     * 新增或者更新
     *
     * @param workflowCase
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(WorkflowCase workflowCase) {

        if (StringUtils.isBlank(workflowCase.getId())) {
            //新增
            //判断是否存在进行中的记录：每个bizId只能有一条status<3的记录
            Long count = workflowCaseMapper.selectCount(new LambdaQueryWrapper<WorkflowCase>()
                    .eq(WorkflowCase::getBizId, workflowCase.getBizId())
                    .eq(WorkflowCase::getTypeFlag,workflowCase.getTypeFlag())
                    .in(WorkflowCase::getStatus, Arrays.asList(0, 1))
            );
            //超额超量预约可以有多条
            if (count > 0
                    && !workflowCase.getTypeFlag().equals(WorkflowType.OVER_RESERVATION.getCode())
                    && !workflowCase.getTypeFlag().equals(WorkflowType.EXCESS_RESERVATION.getCode())) {
                throw new ServiceException("该业务正在走流程中，请勿重复提交！");
            }
            //查询工作流子节点
            List<WorkflowItem> workflowItemList;
            if (CollectionUtil.isNotEmpty(workflowCase.getApproverIds())){
                workflowItemList = workflowItemService.list(new LambdaQueryWrapper<WorkflowItem>()
                        .in(WorkflowItem::getItemId,workflowCase.getApproverIds()));
            }else {
                workflowItemList = workflowItemService.getListByFlowId(workflowCase.getFlowId());
            }


            //1、增加记录
            workflowCase.setCreator(SecurityUtils.getUserNo());
            workflowCase.setStatus(0);
            workflowCase.setAllLevel(workflowItemList.size());
            workflowCase.setCurLevel(workflowItemList.get(0).getLevel());
            workflowCaseMapper.insert(workflowCase);

            //2、新增节点列表
            ArrayList<WorkflowCaseItem> workflowCaseItems = new ArrayList<>();
            int i = 0;
            for (WorkflowItem item : workflowItemList) {
                WorkflowCaseItem workflowCaseItem = new WorkflowCaseItem();
                workflowCaseItem.setCaseId(workflowCase.getId());
                workflowCaseItem.setStatus(i == 0 ? 1 : 0);
                workflowCaseItem.setNotifyStatus(0);
                workflowCaseItem.setItemName(item.getItemName());
                workflowCaseItem.setUserName(item.getUserName());
                workflowCaseItem.setUserNo(item.getUserNo());
                workflowCaseItem.setLevel(item.getLevel());
                workflowCaseItem.setLevelFlag(item.getLevelFlag());
                workflowCaseItems.add(workflowCaseItem);
                i++;
            }
            workflowCaseItemService.saveBatch(workflowCaseItems);

        } else {
            //更新
            //判断是否存在进行中的记录：每个bizId只能有一条status<3的记录
            if (workflowCase.getStatus() < 3 && workflowCase.getStatus() >= 0) {
                Long count = workflowCaseMapper.selectCount(new LambdaQueryWrapper<WorkflowCase>()
                        .ne(WorkflowCase::getId, workflowCase.getId())
                        .eq(WorkflowCase::getBizId, workflowCase.getBizId())
                        .eq(WorkflowCase::getTypeFlag,workflowCase.getTypeFlag())
                        .in(WorkflowCase::getStatus, Arrays.asList(0, 1, 2)));
                if (count > 0) {
                    throw new ServiceException("该业务正在走流程中，请勿重复提交！");
                }
            }
            // 1、更新实例
            workflowCase.setModifier(SecurityUtils.getUserNo());
            workflowCaseMapper.updateById(workflowCase);
            // 2、更新节点
            workflowCaseItemService.updateBatchById(workflowCase.getItemList());

        }
        //TODO sms 发送审核通知
        List<WorkflowCaseItem> caseItemList = workflowCaseItemService.getListByCaseId(workflowCase.getId());
        WorkflowCaseItem curItem = caseItemList.stream().min(Comparator.comparingInt(WorkflowCaseItem::getLevel)).get();
        workflowCaseItemService.notifyNext(curItem, workflowCase);
        return Boolean.TRUE;
    }


    /**
     * 更新实例节点状态
     *
     * @param param 实例节点更新信息
     * @return 修改结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateItemStatus(UpdateCaseItemParam param) {

        //获取实例
        WorkflowCase workflowCase = workflowCaseMapper.selectById(param.getCaseId());
        if (Objects.isNull(workflowCase)) {
            throw new ServiceException("工作流实例不存在！");
        }

        if (workflowCase.getStatus() != 1) {
            throw new ServiceException("该业务尚未开始走流程，请勿提交操作！");
        }

        //获取实例节点
        WorkflowCaseItem caseItem = workflowCaseItemService.getInfoById(param.getItemId());
        if (Objects.isNull(caseItem)) {
            throw new ServiceException("工作流实例节点不存在！");
        }

        if (caseItem.getStatus() != 1) {
            throw new ServiceException("尚未轮到您开始操作环节，请勿提交操作！");
        }

        //判断实例状态是否需要更新
        Boolean notifyNext = Boolean.FALSE;
        Boolean done = Boolean.FALSE;
        if (param.getStatus() == 3) {
            //驳回操作
            workflowCase.setStatus(3);
            workflowCase.setFailText(param.getRemark());
            //通知工作流结束
            done = Boolean.TRUE;
        } else {
            //根据当前节点层级进行相对应的处理
            switch (caseItem.getLevelFlag()) {
                case 0:
                    //通知下一级并将下一级状态改为进行中
                    notifyNext = Boolean.TRUE;
                    break;
                case 1:
                    workflowCase.setStatus(1);
                    //通知下一级并将下一级状态改为进行中
                    notifyNext = Boolean.TRUE;
                    break;
                case 2:
                case 3:
                    workflowCase.setStatus(2);
                    //通知工作流结束
                    done = Boolean.TRUE;
                    break;
                default:
                    break;
            }
        }

        // 1、更新节点
        caseItem.setStatus(param.getStatus());
        caseItem.setRemark(param.getRemark());
        workflowCaseItemService.updateById(caseItem);

        // 2、更新实例
        workflowCase.setModifier(SecurityUtils.getUserNo());
        workflowCase.setCurLevel(caseItem.getLevel());
        workflowCaseMapper.updateById(workflowCase);

        if (notifyNext) {
            //通知下一级并将下一级状态改为进行中
            WorkflowCaseItem nextItem = workflowCaseItemService.getNextItem(caseItem);
            if (Objects.isNull(nextItem)) {
                throw new ServiceException("工作流节点缺失，无法进行审核！");
            }
            workflowCaseItemService.notifyNext(nextItem, workflowCase);
        }
        // 有审核材料权限时，判断一下是否是职业和综合的，如果是的话就审核一下材料
        Boolean b = SecurityUtils.hasRole(RoleAuthName.MATERIAL_MANAGEMENT);
        if (b){
            applicationEventPublisher.publishEvent(new MaterialReviewEvent(new MaterialReviewParam(workflowCase.getBizId(),param.getStatus())));
        }

        if (done) {
            //通知工作流结束
            caseOver(workflowCase);
        }

        return Boolean.TRUE;
    }

    /**
     * 工作流实例结束处理
     *
     * @param workflowCase 工作流实例
     */
    @Override
    public void caseOver(WorkflowCase workflowCase) {
        log.info("工作流实例结束处理：{}", workflowCase);
        //TODO sms 通知工作流负责人

        //执行相应的后续业务
        if (workflowCase.getTypeFlag().equals(WorkflowType.ORDER_FLOW.getCode())
                || workflowCase.getTypeFlag().equals(WorkflowType.ORDER_FLOW_OCCUPATION.getCode())) {
            //订单审核流后续业务
            OrderFlowParam param = new OrderFlowParam("0", workflowCase.getBizId(),
                    workflowCase.getStatus() == 2 ? "0" : "1",
                    workflowCase.getStatus() == 2 ? workflowCase.getFailText() : workflowCase.getRemark());
            applicationEventPublisher.publishEvent(new OrderFlowEvent(param));
        }else if (workflowCase.getTypeFlag().equals(WorkflowType.ORDER_FLOW_CHANGE.getCode())
                ||workflowCase.getTypeFlag().equals(WorkflowType.ORDER_FLOW_CHANGE_OCCUPATIONAL.getCode())) {
            //变更审核流后续业务
            OrderFlowParam param = new OrderFlowParam("1", workflowCase.getBizId(),
                    workflowCase.getStatus() == 2 ? "0" : "1",
                    workflowCase.getStatus() == 2 ? workflowCase.getFailText() : workflowCase.getRemark());
            applicationEventPublisher.publishEvent(new OrderFlowEvent(param));
        }else if (workflowCase.getTypeFlag().equals(WorkflowType.OVER_RESERVATION.getCode())) {
            //跨级审批后续
            OverReservationParam overReservationParam = new OverReservationParam();
            overReservationParam.setId(workflowCase.getId());
            overReservationParam.setStatus(workflowCase.getStatus());
            applicationEventPublisher.publishEvent(new OverReservationEvent(overReservationParam));
        }else if (workflowCase.getTypeFlag().equals(WorkflowType.EXCESS_RESERVATION.getCode())) {
            //超额预约审批后续
            ExcessReservationParam param = new ExcessReservationParam();
            param.setData(workflowCase.getData());
            applicationEventPublisher.publishEvent(new ExcessReservationEvent(param));
        }

    }

    /**
     * 根据业务ID获取实例详情
     *
     * @param bizId    业务ID
     * @param typeFlag 类型标识
     * @return 单条数据
     */
    @Override
    public WorkflowCase getInfoByBzId(String bizId, String typeFlag) {
        List<String> typeFlags = new ArrayList<>();
        typeFlags.add(typeFlag);
        //增加订单变更记录查询
        if(typeFlag.equals(WorkflowType.ORDER_FLOW_OCCUPATION.getCode())){
            typeFlags.add(WorkflowType.ORDER_FLOW_CHANGE_OCCUPATIONAL.getCode());
        }else if (typeFlag.equals(WorkflowType.ORDER_FLOW.getCode())){
            typeFlags.add(WorkflowType.ORDER_FLOW_CHANGE.getCode());
        }
        List<WorkflowCase> workflowCases = workflowCaseMapper.selectList(
                new LambdaQueryWrapper<WorkflowCase>()
                        .eq(WorkflowCase::getBizId, bizId)
                        .in(WorkflowCase::getTypeFlag, typeFlags)
                        .notIn(WorkflowCase::getStatus,-1)
                        .orderByAsc(WorkflowCase::getCreatedate));
        if (CollectionUtil.isEmpty(workflowCases)) {
            throw new ServiceException("该业务尚未走任何流程！");
        }
        WorkflowCase workflowCase = workflowCases.get(workflowCases.size()-1);
        SysBranch sysBranch = iSysBranchService.getByBranchId(workflowCase.getFzxid());
        workflowCase.setFzx(ObjectUtils.isNotEmpty(sysBranch)?sysBranch.getFzx():null);
        workflowCase.setItemList(workflowCaseItemService.getListByCaseId(workflowCase.getId()));
        return workflowCase;
    }


    /**
     * 更新审批流状态
     *
     * @param bizIds   业务ID集合
     * @param typeFlag 工作流类型标识
     * @param status   更新状态
     * @return
     */
    public Boolean loseByBizId(List<String> bizIds, String typeFlag, Integer status, String reason) {
        log.info("更新审批流状态,bizIds:{},typeFlag:{},status:{}",bizIds,typeFlag,status);
        WorkflowCase workflowCase = new WorkflowCase();
        workflowCase.setStatus(status);
        if (status == -1 || status == 3) {
            workflowCase.setFailText(reason);
        } else {
            workflowCase.setRemark(reason);
        }

        workflowCase.setModifier(SecurityUtils.getUserNo());
        workflowCaseMapper.update(workflowCase, new LambdaUpdateWrapper<WorkflowCase>()
                .eq(WorkflowCase::getTypeFlag, typeFlag).in(WorkflowCase::getBizId, bizIds).in(WorkflowCase::getStatus, Arrays.asList(0, 1, 2)));
        return Boolean.TRUE;
    }
}

