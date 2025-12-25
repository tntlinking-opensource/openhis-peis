package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.CrmOrderPlanMapper;
import com.center.medical.datamove.common.bean.model.CrmOrderPlan;
import com.center.medical.datamove.common.service.CrmOrderPlanService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 签单计划(CrmOrderPlan)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:44:59
 */
@Slf4j
@Service("crmOrderPlanService")
@RequiredArgsConstructor
public class CrmOrderPlanServiceImpl extends ServiceImpl<CrmOrderPlanMapper, CrmOrderPlan> implements CrmOrderPlanService {

    private final CrmOrderPlanMapper crmOrderPlanMapper;

    /**
     * 分页查询[签单计划]列表
     *
     * @param page  分页参数
     * @param param CrmOrderPlan查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CrmOrderPlan> getPage(PageParam<CrmOrderPlan> page, CrmOrderPlan param) {
        return crmOrderPlanMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CrmOrderPlan getInfoById(String id) {
        return crmOrderPlanMapper.getInfoById(id);
    }

}


