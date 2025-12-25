package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Report;
import com.center.medical.report.bean.dto.AllContraindicatedDto;
import com.center.medical.report.bean.dto.AllDiseaseDataDto;
import com.center.medical.report.bean.dto.AllReviewDataDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * BG报告主表(Report)表数据库访问层
 *
 * @author ay
 * @since 2023-04-26 17:43:49
 */
public interface ReviewNoticeMapper extends BaseMapper<Report> {

    /**
     * 获取所有可疑职业病模板上所需要的数据
     * @param patientno
     * @return
     */
    List<AllDiseaseDataDto> findAllDiseaseData(@Param("patientno")String patientno);

    /**
     * 获取所有职业禁忌证模板上所需要的数据
     * @param patientno
     * @return
     */
    List<AllContraindicatedDto> findAllContraindicatedData(@Param("patientno")String patientno);

    /**
     * 获取所有复查模板上所需要的数据
     * @param patientno
     * @return
     */
    List<AllReviewDataDto> findAllReviewData(@Param("patientno")String patientno);
}
