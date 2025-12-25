package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.dto.*;
import com.center.medical.bean.model.*;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.dto.*;
import com.center.medical.report.bean.model.BallCheckReport;
import com.center.medical.report.bean.param.GroupHealthParam;
import com.center.medical.report.bean.param.ReportAuditParam;
import com.center.medical.report.bean.vo.GHExportDataVo;
import com.center.medical.report.bean.vo.RAExportDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TJ团检报告主表(BallCheckReport)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:29
 */
public interface BallCheckReportMapper extends BaseMapper<BallCheckReport> {

    /**
     * 分页查询[TJ团检报告主表]列表
     *
     * @param page  分页参数
     * @param param BallCheckReport查询参数
     * @return 分页数据
     */
    IPage<BallCheckReport> getList(PageParam<BallCheckReport> page, @Param("param") BallCheckReport param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    BallCheckReport getInfoById(@Param("id") String id);

    IPage<BallCheckReport> loadBallCheckData(PageParam<BallCheckReport> page, @Param("param") GroupHealthParam param);

    IPage<BallCheckReport> loadAuditBallCheckData(PageParam<BallCheckReport> page, @Param("param") ReportAuditParam param);

    List<FxCompletion> getFxCompletionsBySampleId(@Param("reportId") String reportId);

    List<AgeSqlZyDto> getAgeSqlZy(@Param("reportId") String reportId);

    List<AnalyzeZyDto> dateanalyze_zy(@Param("reportId") String reportId);

    List<DanagerObject> examanalyze_zy(@Param("reportId") String reportId);

    List<FxPersonnelview> getPersonnelData(@Param("reportId") String reportId, @Param("flag") String flag);

    IPage<FxCompletion> finishanalyze_zy(PageParam<Report> page, @Param("reportId") String reportId);

    List<FxReviewsituation> getReviewData(@Param("reportId") String reportId);

    List<FindXmwcqkDto> findXmwcqk(@Param("bhys") boolean bhys, @Param("id") String id);

    List<FindXmcjDto> findXmcj(@Param("bhys") boolean bhys, @Param("id") String id);

    List<FindJctjDto> findJctj(@Param("id") String id);

    List<FindYangxjgDto> findYangxjg(@Param("id") String id);

    List<FindYinxjgDto> findYinxjg(@Param("cid")String cid, @Param("patientCode")String patientCode, @Param("bhys")boolean bhys);

    List<FindDtjzDto> findDtjz(@Param("id") String id);

    Object findUniqueResult(@Param("jhys") String jhys, @Param("sglx") String sglx);

    List<FindZybcxmDto> findZybcxm(@Param("id") String id);

    List<FindRyylDto> findRyyl(@Param("id")String id);

    List<FindFcmxDto> findFcmx(@Param("id")String id);

    List<FindFcqkDto> findFcqk(@Param("id")String id);

    List<FindJcqkhzDto> findJcqkhz(@Param("id")String id);

    List<FindListDateDto> findListDate(@Param("id")String id);

    List<FindJcrsDto> findJcrs(@Param("id")String id);

    int findScrs(@Param("id") String id);

    int findNumByPatientcode(@Param("patientcode") String patientcode);

    List<FindListInDto> findListIn(@Param("patientcode")String patientcode);

    void deleteFxCompletion(@Param("ballIds") String[] ballIds);

    void deleteDanagerObject(@Param("ballIds")String[] ballIds);

    void deleteFxPersonnelview(@Param("ballIds")String[] ballIds);

    void deleteFxReviewsituation(@Param("ballIds")String[] ballIds);

    void deleteFxItemscheck(@Param("ballIds")String[] ballIds);

    void deleteFxDetection(@Param("ballIds")String[] ballIds);

    void deleteFxPositive(@Param("ballIds")String[] ballIds);

    void deleteFxNegative(@Param("ballIds")String[] ballIds);

    void deleteFxDetectionzy(@Param("ballIds")String[] ballIds);

    void deleteFxSummary(@Param("ballIds")String[] ballIds);

    void deleteFxReviewInfo(@Param("ballIds")String[] ballIds);

    void deleteFxHarm(@Param("ballIds")String[] ballIds);

    /**
     * 导出阴性和阳性结果接口
     * @param reportId
     * @return
     */
    List<RAExportDataVo> exportData(@Param("reportId")String reportId);

    /**
     * 导出职业团检样本数据
     * @param patientIds
     * @return
     */
    List<GHExportDataVo> export (@Param("patientIds")List<String> patientIds);

    /**
     * 查询老系统数据
     * @param patientIds
     * @return
     */
    @DataSource(value = DataSourceType.SLAVE)
    List<GHExportDataVo> oldExport(@Param("patientIds")List<String> patientIds);

    /**
     * 查询新系统的e和f
     * @param id
     * @return
     */
    FindJctjDto findJctjEF(String id);
}
