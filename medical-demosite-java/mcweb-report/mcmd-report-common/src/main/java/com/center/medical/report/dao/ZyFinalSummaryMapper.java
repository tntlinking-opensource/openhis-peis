package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.model.ZyFinalSummary;
import com.center.medical.report.bean.param.ZyVsSummaryListParam;
import com.center.medical.report.bean.vo.ZyChooseVo;
import com.center.medical.report.bean.vo.ZyGridDataVo;
import com.center.medical.report.bean.vo.ZyVsSummaryListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 报告分享和报告关系表(ReportShareTwo)数据库访问层
 *
 * @author ay
 * @since 2023-09-19 16:23:51
 */
public interface ZyFinalSummaryMapper extends BaseMapper<ZyFinalSummary> {

    /**
     * 分页查询[报告分享和报告关系表]列表
     *
     * @param page  分页参数
     * @param param ReportShareTwo查询参数
     * @return 分页数据
     */
    IPage<ZyFinalSummary> getPage(PageParam<ZyFinalSummary> page, @Param("param") ZyFinalSummary param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ZyFinalSummary getInfoById(@Param("id") String id);

    /**
     * 获取职业最终结论
     * @param patientcode
     * @return
     */
    List<ZyGridDataVo> getGridData(@Param("patientcode") String patientcode);

    /**
     * 选择危害因素
     * @param patientcode
     * @return
     */
    List<ZyChooseVo> getPendingHarmsList(@Param("patientcode") String patientcode);

    /**
     * 获取职业最终结论
     * @param param
     * @return
     */
    List<ZyVsSummaryListVo> getZyVsSummaryList(@Param("param") ZyVsSummaryListParam param);
}
