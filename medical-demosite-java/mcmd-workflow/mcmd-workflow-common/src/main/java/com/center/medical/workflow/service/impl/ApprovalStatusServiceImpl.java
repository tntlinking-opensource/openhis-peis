package com.center.medical.workflow.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.workflow.bean.param.ApprovalStatusParam;
import com.center.medical.workflow.bean.vo.ApprovalStatusVo;
import com.center.medical.workflow.dao.ApprovalStatusMapper;
import com.center.medical.workflow.service.ApprovalStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * QT体检者表(Peispatient)服务实现类
 *
 * @author ay
 * @since 2024-03-29 13:37:41
 */
@Slf4j
@Service("approvalStatusService")
@RequiredArgsConstructor
public class ApprovalStatusServiceImpl extends ServiceImpl<ApprovalStatusMapper, Peispatient> implements ApprovalStatusService {

    private final ApprovalStatusMapper approvalStatusMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ApprovalStatusVo> getPage(PageParam<ApprovalStatusVo> page, ApprovalStatusParam param) {
        //没有决策管理权限就只能看自己的
        Boolean role = SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        if (!role) {
            param.setUserNo(SecurityUtils.getUserNo());
        }
        return approvalStatusMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return approvalStatusMapper.getInfoById(id);
    }

}

