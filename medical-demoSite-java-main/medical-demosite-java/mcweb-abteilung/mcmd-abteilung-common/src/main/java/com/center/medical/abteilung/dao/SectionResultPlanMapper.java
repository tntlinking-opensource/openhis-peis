package com.center.medical.abteilung.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.dto.ItemsDetailDto;
import com.center.medical.abteilung.bean.model.SectionResultPlan;
import com.center.medical.abteilung.bean.param.SectionResultPlanParam;
import com.center.medical.abteilung.bean.vo.SectionResultPlanVo;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 科室批量录入结果(SectionResultPlan)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:19
 */
public interface SectionResultPlanMapper extends BaseMapper<SectionResultPlan> {

    /**
     * 分页查询[科室批量录入结果]列表
     *
     * @param page  分页参数
     * @param sectionResultPlanParam SectionResultPlan查询参数
     * @return 分页数据
     */
    IPage<SectionResultPlanVo> getPage(PageParam<SectionResultPlanVo> page, @Param("param") SectionResultPlanParam sectionResultPlanParam);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SectionResultPlan getInfoById(@Param("id") String id);

    /**
     * 查询项目详情
     * @param inputCode
     * @param ksID
     * @return
     */
    List<ItemsDetailDto> getItemsDetail(@Param("inputCode") String inputCode,@Param("ksID") String ksID);
}
