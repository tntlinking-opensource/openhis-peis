package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Report;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.dto.ChartDataDto;
import com.center.medical.report.bean.dto.GetReportDto;
import com.center.medical.report.bean.dto.HealthAssociateExportDto;
import com.center.medical.report.bean.model.ExportStatistics;
import com.center.medical.report.bean.param.*;
import com.center.medical.report.bean.vo.GetReportUrlVo;
import com.center.medical.report.bean.vo.PhoneInformVo;
import com.center.medical.report.bean.vo.ReportRemindVo;
import com.center.medical.report.bean.vo.TotalAuditVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * BG报告主表(Report)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-23 17:11:14
 */
public interface ReportMapper extends BaseMapper<Report> {

    /**
     * 分页查询[BG报告主表]列表
     *
     * @param page  分页参数
     * @param param Report查询参数
     * @return 分页数据
     */
    IPage<Report> getPage(PageParam<Report> page, @Param("param") ReportParam param);

    /**
     * 分页查询体检报告待领提醒
     *
     * @param page  分页参数
     * @param param Report查询参数
     * @return 分页数据
     */
    IPage<ReportRemindVo> getReportRemindPage(PageParam<Report> page, @Param("param") ReportRemindParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Report getInfoById(@Param("id") String id);

    Report getInfoByPatientcode(@Param("patientcode") String patientcode, @Param("diseaseHealth") int diseaseHealth);

    /**
     * 分页查询职业报告交接
     *
     * @param page
     * @param param
     * @return
     */
    IPage<Report> getPaPage(PageParam<Report> page, @Param("param") ProfessionAssociateParam param);

    /**
     * 不分页查询职业报告交接导出数据
     *
     * @param param
     * @return
     */
    List<Report> getPaList(@Param("param") ProfessionAssociateParam param);

    /**
     * 查询报告交接统计数据
     *
     * @param param
     * @return
     */
    List<ExportStatistics> exportStatistics(@Param("param") ProfessionAssociateParam param, @Param("healthdisease") int healthdisease);

    /**
     * 查询折线图数据
     *
     * @param time
     * @param diseaseHealth
     * @return
     */
    List<ChartDataDto> getChartData(@Param("time") String time, @Param("diseaseHealth") int diseaseHealth);

    /**
     * 职业报告领取通知分页查询
     *
     * @param page
     * @param param
     * @param type
     * @return
     */
    IPage<PhoneInformVo> getReceiveReportData(PageParam<PhoneInformVo> page, @Param("param") PhoneInformParam param, @Param("type") int type, @Param("diseasehealth") int diseasehealth);

    /**
     * 获取职业报告领取通知导出数据
     *
     * @param param
     * @param diseasehealth
     * @return
     */
    List<PhoneInformVo> exportNoticeReport(@Param("param") PhoneInformParam param, @Param("diseasehealth") int diseasehealth);

    /**
     * 获取导出职业报告领取数据
     *
     * @param param
     * @param dh
     * @return
     */
    List<GetReportDto> exportGetReport(@Param("param") PhoneInformParam param, @Param("diseasehealth") int dh);

    /**
     * 获取一审的数据
     *
     * @param page
     * @param param
     * @return
     */
    List<TotalAuditVo> getFirst(PageParam<Report> page, @Param("param") TotalAuditParam param);

    /**
     * 获取二审的数据
     *
     * @param page
     * @param param
     * @return
     */
    List<TotalAuditVo> getSecond(PageParam<Report> page, @Param("param") TotalAuditParam param);

    /**
     * 获取终审的数据
     *
     * @param page
     * @param param
     * @return
     */
    List<TotalAuditVo> getLast(PageParam<Report> page, @Param("param") TotalAuditParam param);

    /**
     * 获取报告未出通知
     * @return
     */
    Long getReportNotReleasedCount();

    /**
     * 获取未总检的数量
     * @return
     */
    Long getNotTotalInspectionCount();

    /**
     * 获取报告未出数量
     * @param num
     * @return
     */
    String getReportNotReleasedTime(@Param("num") String num);

    /**
     * 获取报告url
     * @param param
     * @return
     */
    GetReportUrlVo getReportUrl(@Param("param") GetReportUrlParam param);

    /**
     * 报告交接导出数据
     * @param param
     * @return
     */
    List<HealthAssociateExportDto> healthAssociateExport(@Param("param") ProfessionAssociateParam param);

    /**
     * 获取报告地址
     * @param patientcodes
     * @param diseaseHealth
     * @return
     */
    List<String> getReportAddress(@Param("patientcodes") List<String> patientcodes,@Param("diseaseHealth") Integer diseaseHealth);
}
