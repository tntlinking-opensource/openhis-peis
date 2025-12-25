package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.FxDetection;
import com.center.medical.bean.model.Report;
import com.center.medical.bean.vo.CheckanalyzeVo;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 综合分析-检出统计、团体小结（健康）(FxDetection)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:15
 */
public interface FxDetectionMapper extends BaseMapper<FxDetection> {

    /**
     * 分页查询[综合分析-检出统计、团体小结（健康）]列表
     *
     * @param page  分页参数
     * @param param FxDetection查询参数
     * @return 分页数据
     */
    IPage<FxDetection> getList(PageParam<FxDetection> page, @Param("param") FxDetection param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    FxDetection getInfoById(@Param("id") String id);

    /**
     * 检出统计
     * @param reportId
     * @return
     */
    IPage<CheckanalyzeVo> checkanalyze(PageParam<Report> page,@Param("reportId")String reportId);
}
