package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.ReportContent;
import com.center.medical.bean.param.ReportContentParam;
import org.apache.ibatis.annotations.Param;

/**
 * 生成报告内容(ReportContent)表数据库访问层
 *
 * @author ay
 * @since 2023-05-18 10:43:56
 */
public interface ReportContentMapper extends BaseMapper<ReportContent> {


    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReportContent getInfoById(@Param("id") String id);


    /**
     * 根据体检号获取记录详情
     *
     * @param patientcode 体检号
     * @return 详情信息
     */
    ReportContent getInfoByPatientcode(@Param("patientcode") String patientcode);


    /**
     * 根据体检号获取记录详情
     *
     * @param analyzeId 团检样本id
     * @return 详情信息
     */
    ReportContent getInfoByAnalyzeId(@Param("analyzeId") String analyzeId);

    /**
     * 查询报告内容
     * @param param
     * @return
     */
    ReportContent findReport(@Param("param")ReportContentParam param);
}
