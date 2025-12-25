package com.center.medical.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.*;
import com.center.medical.bean.vo.CheckanalyzeVo;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.model.BallCheckReport;
import com.center.medical.report.bean.param.GHSaveDataParam;
import com.center.medical.report.bean.param.GroupHealthParam;
import com.center.medical.report.bean.param.ImgJkParam;
import com.center.medical.report.bean.param.ReportAuditParam;
import com.center.medical.report.bean.vo.BallCheckReportVo;
import com.center.medical.report.bean.vo.GHExportDataVo;
import com.center.medical.report.bean.vo.RAExportDataVo;
import com.center.medical.sellcrm.bean.model.Createorder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * TJ团检报告主表(BallCheckReport)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:29
 */
public interface BallCheckReportService extends IService<BallCheckReport> {

    /**
     * 分页查询[TJ团检报告主表]列表
     *
     * @param page  分页参数
     * @param param BallCheckReport查询参数
     * @return 分页数据
     */
    IPage<BallCheckReport> getList(PageParam<BallCheckReport> page, BallCheckReport param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    BallCheckReport getInfoById(String id);

    /**
     * 查询团检报告主表
     *
     * @param param param
     */
    IPage<BallCheckReport>  loadBallCheckData(PageParam<BallCheckReport> page, GroupHealthParam param);

    /**
     * 保存团检报告主表
     *
     * @param ballCheckReportVo ballCheckReportVo
     */
    String saveBallCheckData(BallCheckReportVo ballCheckReportVo);

    String addpeople(String reportId);

    String removeReport(String ids);

    List<Peispatient> addSamplePersonData(String groupId, String idOrgid, String reportId, String key, String flag);

    String saveData(GHSaveDataParam param);

    List<GHExportDataVo> export(String groupId, String reportId);

    IPage<BallCheckReport> loadAuditBallCheckData(PageParam<BallCheckReport> page, ReportAuditParam param);

    String commit(String ids);

    String recall(String ids);

    List<Createorder> addBallCheckReportOrderData(String groupId);

    List<HashMap> loadSamplePersonViewData(String id);

    String finish(String id, String spyj, String spjg);

    String unfinish(String ids);

    List<HashMap> peopleanalyze_zy(String reportId);

    List<HashMap> ageanalyze_zy(String reportId);

    List<HashMap> dateanalyze_zy(String reportId);

    List<DanagerObject> examanalyze_zy(String reportId);

    List<FxPersonnelview> getPersonnelData(String reportId, String flag);

    IPage<FxCompletion> finishanalyze_zy(PageParam<Report> page, String reportId);

    List<FxReviewsituation> getReviewData(String reportId);

    String saveAllImgJk(ImgJkParam imgJkParam) throws IOException;

    String generateAnalyse(String ids, String flag);

    /**
     * 修正
     * @param ids
     * @return
     */
    Boolean undo(List<String> ids);

    /**
     * 主检审核
     * @param id
     * @return
     */
    Boolean mainAudit(String id);

    /**
     * 参检分析
     * @param reportId
     * @param groupId
     * @return
     */
    List<FxItemscheck> examanalyze(String reportId, String groupId);

    /**
     * 检出统计
     * @param reportId
     * @param groupId
     * @return
     */
    IPage<CheckanalyzeVo> checkanalyze(PageParam<Report> page,String reportId, String groupId);

    /**
     * 异常前十 检出统计
     * @param reportId
     * @param groupId
     * @return
     */
    HashMap loadcheckanalyze(String reportId, String groupId);

    /**
     * 导出阴性和阳性结果接口
     * @param reportId
     * @return
     */
    List<RAExportDataVo> exportData(String reportId);
}

