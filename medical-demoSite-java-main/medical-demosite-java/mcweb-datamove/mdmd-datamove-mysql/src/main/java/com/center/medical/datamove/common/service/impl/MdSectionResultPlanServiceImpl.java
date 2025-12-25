package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdSectionResultPlanMapper;
import com.center.medical.datamove.common.bean.model.MdSectionResultPlan;
import com.center.medical.datamove.common.service.MdSectionResultPlanService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 科室批量录入结果(MdSectionResultPlan)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:35
 */
@Slf4j
@Service("mdSectionResultPlanService")
@RequiredArgsConstructor
public class MdSectionResultPlanServiceImpl extends ServiceImpl<MdSectionResultPlanMapper, MdSectionResultPlan> implements MdSectionResultPlanService {

    private final MdSectionResultPlanMapper mdSectionResultPlanMapper;

    /**
     * 分页查询[科室批量录入结果]列表
     *
     * @param page  分页参数
     * @param param MdSectionResultPlan查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdSectionResultPlan> getPage(PageParam<MdSectionResultPlan> page, MdSectionResultPlan param) {
        return mdSectionResultPlanMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdSectionResultPlan getInfoById(String id) {
        return mdSectionResultPlanMapper.getInfoById(id);
    }

}


