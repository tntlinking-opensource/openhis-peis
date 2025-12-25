package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Report;
import com.center.medical.report.bean.dto.GetPrXjdataDto;
import com.center.medical.report.bean.dto.PrYsxmDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 隐私报告(Report)表数据库访问层
 *
 * @author ay
 * @since 2023-04-26 17:43:49
 */
public interface PrivateReportMapper extends BaseMapper<Report> {

    /**
     * 生成隐私小结，正常小结中不包含隐私项目
     * @param patientcode
     * @return
     */
    List<GetPrXjdataDto> getPrXjdata(@Param("patientcode")String patientcode);

    /**
     * 获取隐私项目
     * @param patientcode
     * @return
     */
    List<PrYsxmDto> getPrYsxm(@Param("patientcode")String patientcode);
}
