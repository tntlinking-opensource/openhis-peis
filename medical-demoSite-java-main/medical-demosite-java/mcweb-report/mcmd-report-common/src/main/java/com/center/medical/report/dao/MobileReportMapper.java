package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.dto.ReportListDto;
import com.center.medical.bean.dto.ShareReportDto;
import com.center.medical.bean.model.NewMobileReportParam;
import com.center.medical.bean.model.ReportContent;
import com.center.medical.bean.param.ValidationCodeParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 生成报告内容(ReportContent)数据库访问层
 *
 * @author ay
 * @since 2023-08-14 14:54:49
 */
public interface MobileReportMapper extends BaseMapper<ReportContent> {

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReportContent getInfoById(@Param("id") String id);

    /**
     * 根据手机号查询体检者档案
     *
     * @param phone
     * @return
     */
    List<String> getPatientCodes(@Param("phone") String phone,@Param("year") String year);

    /**
     * 查询报告列表
     *
     * @param patientCodes
     * @return
     */
    List<ReportListDto> getReportList(@Param("patientCodes") List<String> patientCodes);

    /**
     * 获取分享报告列表数据
     *
     * @param param
     * @return
     */
    List<ShareReportDto> getShareReportList(@Param("param") ValidationCodeParam param);

    /**
     * 新版小程序查询手机报告列表
     * @param param
     * @return
     */
    List<ReportListDto> getNewReportList(@Param("param") NewMobileReportParam param);

    /**
     * 根据订单号、手机号查询报告列表
     * @param phone
     * @param orderNum
     * @return
     */
    List<ReportListDto> getReportListByOrderId(@Param("phone") String phone, @Param("orderNum") String orderNum);

    /**
     * 查询体检号对应的手机号
     * @param patientcode
     * @return
     */
    String getCheckDetailsPhone(@Param("patientcode") String patientcode);
}
