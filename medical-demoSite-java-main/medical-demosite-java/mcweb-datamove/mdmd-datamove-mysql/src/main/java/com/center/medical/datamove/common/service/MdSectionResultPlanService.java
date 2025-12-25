package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdSectionResultPlan;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 科室批量录入结果(MdSectionResultPlan)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:34
 */
public interface MdSectionResultPlanService extends IService<MdSectionResultPlan> {

    /**
     * 分页查询[科室批量录入结果]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdSectionResultPlan> getPage(PageParam<MdSectionResultPlan> page, MdSectionResultPlan param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSectionResultPlan getInfoById(String id);

}

