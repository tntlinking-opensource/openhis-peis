package com.center.medical.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.dto.GetReportDto;
import com.center.medical.report.bean.dto.HealthAssociateExportDto;
import com.center.medical.report.bean.model.ExportStatistics;
import com.center.medical.report.bean.model.Formdata;
import com.center.medical.bean.model.Report;
import com.center.medical.report.bean.param.*;
import com.center.medical.report.bean.vo.GetReportUrlVo;
import com.center.medical.report.bean.vo.PhoneInformVo;
import com.center.medical.report.bean.vo.ReportRemindVo;
import com.center.medical.report.bean.vo.TotalAuditVo;
import com.center.medical.reservation.bean.param.ReceivingReportsParam;

import java.util.List;
import java.util.Map;

/**
 * BG报告主表(Report)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-23 17:11:14
 */
public interface ReportService extends IService<Report> {

    /**
     * 分页查询[BG报告主表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Report> getPage(PageParam<Report> page, ReportParam param);

    /**
     * 分页查询体检报告待领提醒
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ReportRemindVo> getReportRemindPage(PageParam<Report> page, ReportRemindParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Report getInfoById(String id);

    Report getInfoByPatientcode(String patientcode, int diseaseHealth);

    /**
     * 分页查询职业报告交接
     * @param page
     * @param param
     * @return
     */
    IPage<Report> getPaPage(PageParam<Report> page, ProfessionAssociateParam param);

    /**
     * 批量通过职业报告交接
     * @param param
     * @return
     */
    Boolean saOrUp(FormdataAndGriddataParam param) throws Exception;

    /**
     * 职业报告交接反交接
     * @param param
     * @return
     */
    Boolean unaudit(FormdataAndGriddataParam param);

    /**
     * 批量编辑柜子号保存
     * @param formdata
     * @return
     */
    Boolean saveEdit(Formdata formdata);

    /**
     * 不分页查询职业报告交接导出数据
     * @param param
     * @return
     */
    List<Report> getPaList(ProfessionAssociateParam param);

    /**
     * 查询报告交接统计数据
     * @param param
     * @param i
     * @return
     */
    List<ExportStatistics> exportStatistics(ProfessionAssociateParam param, int i);

    /**
     * 折线图数据
     * @param diseaseHealth
     * @return
     */
    Map<String, Object> getChartData(int diseaseHealth);

    /**
     * 职业报告领取通知分页查询
     * @param page
     * @param param
     * @param type
     * @param diseasehealth
     * @return
     */
    IPage<PhoneInformVo> getReceiveReportData(PageParam<PhoneInformVo> page, PhoneInformParam param, int type, int diseasehealth);

    /**
     * 通知
     * @param noticeParam
     * @return
     */
    Boolean notice(NoticeParam noticeParam);

    /**
     * 保存短信
     * @param formdata
     * @param diseasehealth
     * @return
     */
    String sendMsg(SendMsgFormDataParam formdata, int diseasehealth);

    /**
     * 取消发送
     * @param patientcodes
     * @param diseaseHealth
     * @return
     */
    Boolean cancelSMS(List<String> patientcodes, int diseaseHealth);

    /**
     * 获取职业报告领取通知导出数据
     * @param param
     * @param dh
     * @return
     */
    List<PhoneInformVo> exportNoticeReport(PhoneInformParam param, int dh);

    /**
     * 领取
     * @param param
     * @param diseaseHealth
     * @return
     */
    Boolean receive(ReceiveFromDataParam param, int diseaseHealth);

    /**
     * 反领取
     * @param ids
     * @param diseaseHealth
     * @return
     */
    Boolean unNotice(List<String> ids, int diseaseHealth);

    /**
     * 获取导出职业报告领取数据
     * @param param
     * @param dh
     * @return
     */
    List<GetReportDto> exportGetReport(PhoneInformParam param, int dh);

    /**
     * 分页查询报告审核统计
     * @param page
     * @param param
     * @return
     */
    List<TotalAuditVo> getTotalAuditPage(PageParam<Report> page, TotalAuditParam param);

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
    String getReportNotReleasedTime(String num);

    /**
     * 获取报告url
     * @param param
     * @return
     */
    GetReportUrlVo getReportUrl(GetReportUrlParam param);

    /**
     * 接收报告
     * @param param
     * @return
     */
    Boolean receivingReports(ReceivingReportsParam param);

    /**
     * 报告交接导出数据
     * @param param
     * @return
     */
    List<HealthAssociateExportDto> healthAssociateExport(ProfessionAssociateParam param);

    /**
     * 获取报告地址
     * @param patientcodes
     * @param diseaseHealth
     * @return
     */
    List<String> getReportAddress(List<String> patientcodes, Integer diseaseHealth);
}

