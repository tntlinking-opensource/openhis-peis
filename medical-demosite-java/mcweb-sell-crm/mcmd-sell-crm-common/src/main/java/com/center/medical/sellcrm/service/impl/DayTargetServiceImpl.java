package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Selltarget;
import com.center.medical.sellcrm.bean.param.DayTargetParam;
import com.center.medical.sellcrm.bean.vo.DayTargetVo;
import com.center.medical.sellcrm.dao.DayTargetMapper;
import com.center.medical.sellcrm.service.DayTargetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * XS销售目标(CrmSelltarget)服务实现类
 *
 * @author ay
 * @since 2024-01-22 11:13:07
 */
@Slf4j
@Service("crmSelltargetService")
@RequiredArgsConstructor
public class DayTargetServiceImpl extends ServiceImpl<DayTargetMapper, Selltarget> implements DayTargetService {

    private final DayTargetMapper dayTargetMapper;

    /**
     * 分页查询[XS销售目标]列表
     *
     * @param page  分页参数
     * @param param CrmSelltarget查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DayTargetVo> getPage(PageParam<DayTargetVo> page, DayTargetParam param) {
        Boolean isLeader = SecurityUtils.isLeader() || SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        if (!isLeader) {
            param.setUserNo(SecurityUtils.getUserNo());
        }
        return dayTargetMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Selltarget getInfoById(String id) {
        return dayTargetMapper.getInfoById(id);
    }

    /**
     * 获取总结数据
     * @param param
     * @return
     */
    @Override
    public Double getSummaryData(DayTargetParam param) {
        Boolean isLeader = SecurityUtils.isLeader() || SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        if (!isLeader) {
            param.setUserNo(SecurityUtils.getUserNo());
        }
        return dayTargetMapper.getSummaryData(param);
    }

    /**
     * 导出销售日目标
     * @param param
     * @return
     */
    @Override
    public List<DayTargetVo> exportData(DayTargetParam param) {
        Boolean isLeader = SecurityUtils.isLeader() || SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        if (!isLeader) {
            param.setUserNo(SecurityUtils.getUserNo());
        }
        return dayTargetMapper.exportData(param);
    }
}

