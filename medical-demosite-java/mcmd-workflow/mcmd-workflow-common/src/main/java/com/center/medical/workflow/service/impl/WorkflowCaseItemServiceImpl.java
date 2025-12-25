package com.center.medical.workflow.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.enums.NoticeConfigId;
import com.center.medical.bean.param.AddNotificationParam;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.service.ISysUserService;
import com.center.medical.system.service.SysNotificationService;
import com.center.medical.workflow.bean.enums.WorkflowType;
import com.center.medical.workflow.bean.model.WorkflowCase;
import com.center.medical.workflow.bean.model.WorkflowCaseItem;
import com.center.medical.workflow.dao.WorkflowCaseItemMapper;
import com.center.medical.workflow.dao.WorkflowCaseMapper;
import com.center.medical.workflow.service.WorkflowCaseItemService;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * 实例节点(WorkflowCaseItem)服务实现类
 *
 * @author makejava
 * @since 2023-11-08 16:58:11
 */
@Slf4j
@Service("workflowCaseItemService")
@RequiredArgsConstructor
public class WorkflowCaseItemServiceImpl extends ServiceImpl<WorkflowCaseItemMapper, WorkflowCaseItem> implements WorkflowCaseItemService {

    private final WorkflowCaseItemMapper workflowCaseItemMapper;
    private final WorkflowCaseMapper workflowCaseMapper;
    private final ISysUserService iSysUserService;
    private final SysNotificationService sysNotificationService;

    /**
     * 分页查询[实例节点]列表
     *
     * @param page  分页参数
     * @param param WorkflowCaseItem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WorkflowCaseItem> getPage(PageParam<WorkflowCaseItem> page, WorkflowCaseItem param) {
        return workflowCaseItemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键itemId
     * @return 详情信息
     */
    @Override
    public WorkflowCaseItem getInfoById(String id) {
        return workflowCaseItemMapper.getInfoById(id);
    }

    /**
     * 根据工实例ID获取节点列表
     *
     * @param caseId
     * @return
     */
    @Override
    public List<WorkflowCaseItem> getListByCaseId(String caseId) {
        return workflowCaseItemMapper.getListByCaseId(caseId);
    }

    /**
     * 根据节点id获取节点列表
     *
     * @param caseItem 当前节点
     * @return
     */
    @Override
    public WorkflowCaseItem getNextItem(WorkflowCaseItem caseItem) {
        //更新下一级节点为进行中
        List<WorkflowCaseItem> itemList = workflowCaseItemMapper.getListByCaseId(caseItem.getCaseId());
        WorkflowCaseItem nextItem = null;
        Boolean isNext = Boolean.FALSE;
        for (WorkflowCaseItem item : itemList) {
            if (isNext) {
                nextItem = item;
                break;
            }
            if (item.getLevel() == caseItem.getLevel()) {
                isNext = Boolean.TRUE;
            }
        }
        return nextItem;
    }

    /**
     * 通知并将节点状态改为进行中
     *
     * @param caseItem     实例节点
     * @param workflowCase 工作流实例
     */
    @Override
    public void notifyNext(WorkflowCaseItem caseItem, WorkflowCase workflowCase) {
        //更改实例节点状态
        caseItem.setStatus(1);
        caseItem.setNotifyStatus(0);
        workflowCaseItemMapper.updateById(caseItem);

        //通知操作人进行操作(根据notifyType进行消息推送通知)
        String[] array = null;
        String phonenumber = "";

        if (workflowCase.getTypeFlag().equals(WorkflowType.ORDER_FLOW.getCode())
                || workflowCase.getTypeFlag().equals(WorkflowType.ORDER_FLOW_OCCUPATION.getCode())) {
            //订单审核通知
            String userNo = SecurityUtils.getUserNo();
            SysUser user = iSysUserService.getUserByNo(userNo);
            phonenumber = user.getPhonenumber();
            array = new String[]{user.getUserName(), workflowCase.getCaseName()};
        }
        if (workflowCase.getTypeFlag().equals(WorkflowType.ORDER_FLOW_CHANGE.getCode())
                || workflowCase.getTypeFlag().equals(WorkflowType.ORDER_FLOW_CHANGE_OCCUPATIONAL.getCode())) {
            //订单变更审核通知
            String userNo = SecurityUtils.getUserNo();
            SysUser user = iSysUserService.getUserByNo(userNo);
            phonenumber = user.getPhonenumber();
            array = new String[]{user.getUserName(), workflowCase.getCaseName()};
        }

        //发送站内通知
        AddNotificationParam param = new AddNotificationParam(caseItem.getUserNo(),
                NoticeConfigId.ORDER_REVIEW.value(),workflowCase.getCaseName());
        sysNotificationService.addNotice(param);
        if (StringUtils.isNotBlank(phonenumber)) {
            Boolean sendSuccess = Boolean.FALSE;
            try {
                sendMsg(phonenumber, array);
                sendSuccess = Boolean.TRUE;
            } catch (Exception e) {
                log.error("工作流实列通知操作人进行操作发送通知失败：{}", e);
            }
            if (sendSuccess) {
                //通知成功，改为已通知
                caseItem.setNotifyStatus(1);
                caseItem.setNotifyTime(new Date());
                workflowCaseItemMapper.updateById(caseItem);
            }
        } else {
            log.error("工作流实列通知操作人进行操作发送通知失败：通知电话号为空");
        }


        //判断是否需要更改实例的状态
        if (caseItem.getLevelFlag() == 1 || caseItem.getLevelFlag() == 3) {
            //如果是第一级则将实例状态改为进行中
            if (Objects.isNull(workflowCase)) {
                throw new ServiceException("执行的业务主体不存在！");
            }
            if (workflowCase.getStatus() == 0) {
                //将实例状态改为进行中
                workflowCase.setStatus(1);
                workflowCase.setModifier(SecurityUtils.getUserNo());
                workflowCaseMapper.updateById(workflowCase);
            }
        }
    }

    /**
     * @param phoneNum
     * @param code
     * @throws Exception void
     * @Title: sendMsg
     * @author mbx
     * @since 2016年9月29日 V 1.0
     */
    @SuppressWarnings("unused")
    public void sendMsg(String phoneNum, String[] code) throws Exception {
        HashMap<String, Object> result = null;
        //初始化SDK
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();

        //******************************注释*********************************************
        //*初始化服务器地址和端口                                                       *
        //*沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
        //*生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883");       *
        //*******************************************************************************
        restAPI.init("app.cloopen.com", "8883");

        //******************************注释*********************************************
        //*初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN     *
        //*ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
        //*参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。                   *
        //*******************************************************************************
//        restAPI.setAccount("8a48b5515388ec1501539c68d0d51c7c", "abe719756b4b413f86ec9d3a4b776547");
        restAPI.setAccount("8aaf07085a608ec2015a6a597d9b0520", "f9aabc7ee53a4dc9a6a70ecc1952fe54");

        //******************************注释*********************************************
        //*初始化应用ID                                                                 *
        //*测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID     *
        //*应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
        //*******************************************************************************
//        restAPI.setAppId("8aaf0708575066050157509c4a2d00f6");
        restAPI.setAppId("8aaf07085a6ec238015a890f83be0e1e");

        //**************************************举例说明***********************************************************************
        //*假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为           *
        //*result = restAPI.sendTemplateSMS("13800000000","1" ,new String[]{"6532","5"});                                                                         *
        //*则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入     *
        //*********************************************************************************************************************
        result = restAPI.sendTemplateSMS(phoneNum, "157004", code);
    }

}

