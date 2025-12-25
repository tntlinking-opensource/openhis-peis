package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.SectionResultPlanMapper;
import com.center.medical.datamove.oracle.bean.model.SectionResultPlan;
import com.center.medical.datamove.oracle.service.SectionResultPlanService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (SectionResultPlan)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:44
 */
@Slf4j
@Service("sectionResultPlanService")
@RequiredArgsConstructor
public class SectionResultPlanServiceImpl extends ServiceImpl<SectionResultPlanMapper, SectionResultPlan> implements SectionResultPlanService {

    private final SectionResultPlanMapper sectionResultPlanMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param SectionResultPlan查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SectionResultPlan> getPage(PageParam<SectionResultPlan> page, SectionResultPlan param) {
        return sectionResultPlanMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SectionResultPlan getInfoById(String id) {
        return sectionResultPlanMapper.getInfoById(id);
    }

}


