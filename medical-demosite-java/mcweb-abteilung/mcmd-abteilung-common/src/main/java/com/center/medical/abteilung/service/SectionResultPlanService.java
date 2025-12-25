package com.center.medical.abteilung.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.abteilung.bean.model.SectionResultPlan;
import com.center.medical.abteilung.bean.param.SectionResultPlanParam;
import com.center.medical.abteilung.bean.vo.SectionResultPlanVo;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * 科室批量录入结果(SectionResultPlan)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:19
 */
public interface SectionResultPlanService extends IService<SectionResultPlan> {

    /**
     * 分页查询[科室批量录入结果]列表
     *
     * @param page  分页参数
     * @param sectionResultPlanParam 查询参数
     * @return 分页数据
     */
    IPage<SectionResultPlanVo> getPage(PageParam<SectionResultPlanVo> page, SectionResultPlanParam sectionResultPlanParam);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SectionResultPlan getInfoById(String id);

    /**
     * 检查体检号
     * @param patientcode
     * @param depId
     * @return
     */
    Boolean checkCode(String patientcode, String depId);

    /**
     * 添加
     * @param sectionResultPlans
     * @return
     */
    Boolean saOrUp(List<SectionResultPlan> sectionResultPlans);

    /**
     * 科室审核
     * @param sectionResultPlan
     */
    void audit(SectionResultPlan sectionResultPlan);
}

