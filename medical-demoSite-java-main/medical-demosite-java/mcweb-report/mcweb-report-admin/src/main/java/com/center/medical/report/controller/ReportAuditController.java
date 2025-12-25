package com.center.medical.report.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.*;
import com.center.medical.bean.vo.CheckanalyzeVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.report.bean.model.BallCheckReport;
import com.center.medical.report.bean.param.ImgJkParam;
import com.center.medical.report.bean.param.ReportAuditParam;
import com.center.medical.report.bean.vo.RAExportDataVo;
import com.center.medical.report.service.BallCheckReportService;
import com.center.medical.service.FxDetectionService;
import com.center.medical.service.FxPositiveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


/**
 * 团检报告审批
 *
 * @author 浮俊杰
 * @since 2022-12-16 20:37:35
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "团检报告-团检报告审批")
@RequestMapping("/group/report_audit")
public class ReportAuditController extends BaseController {
    /**
     * 服务对象
     */
    private final BallCheckReportService ballCheckReportService;
    private final FxDetectionService fxDetectionService;
    private final MapperFacade mapperFacade;
    private final FxPositiveService fxPositiveService;

    /**
     * 团检报告分页查询
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/loadAuditBallCheckData")
    @ApiOperation(value = "团检报告审批分页查询", notes = "团检报告审批和团建报告打印分页查询")
    public R<IPage<BallCheckReport>> getListData(PageParamSimple pageParamSimple, ReportAuditParam param) {
        PageParam<BallCheckReport> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.ballCheckReportService.loadAuditBallCheckData(page, param));
    }

    /**
     * 提交
     *
     * @param ids 查询条件
     * @return 所有数据
     */
    @GetMapping("/commit/{ids}")
    @ApiOperation(value = "提交", notes = "提交")
    @ApiImplicitParam(name = "ids", value = "要查询的对象的多个主键{id}")
    public R commit(@PathVariable String ids) {
        return R.ok(this.ballCheckReportService.commit(ids));
    }

    /**
     * 撤回
     *
     * @param ids 查询条件
     * @return 所有数据
     */
    @GetMapping("/recall/{ids}")
    @ApiOperation(value = "撤回", notes = "撤回")
    @ApiImplicitParam(name = "ids", value = "要撤回的对象的多个主键{id}")
    public R recall(@PathVariable String ids) {
        return R.ok(this.ballCheckReportService.recall(ids));
    }

    /**
     * 审核通过和不通过
     *
     * @param id,spyj,spjg
     * @return 审核结果
     */
    @GetMapping("/finish/{id}/{spyj}/{spjg}")
    @ApiOperation(value = "审核", notes = "审核通过和审核不通过")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID"),
            @ApiImplicitParam(name = "spyj", value = "审批意见"),
            @ApiImplicitParam(name = "spjg", value = "审批结果")
    })
    public R finish(@PathVariable String id, @PathVariable String spyj, @PathVariable String spjg) {
        return R.ok(this.ballCheckReportService.finish(id, spyj, spjg));
    }

    /**
     * 反审核
     *
     * @param ids 查询条件
     * @return 所有数据
     */
    @GetMapping("/unfinish/{ids}")
    @ApiOperation(value = "反审核", notes = "反审核")
    @ApiImplicitParam(name = "ids", value = "反审核的对象的多个主键{id}")
    public R unfinish(@PathVariable String ids) {
        return R.ok(this.ballCheckReportService.unfinish(ids));
    }

    /**
     * 人员分析 职业+健康
     *
     * @param reportId 查询条件
     * @return 所有数据
     */
    @GetMapping("/peopleanalyze_zy/{reportId}")
    @ApiOperation(value = "人员构成", notes = "人员构成")
    @ApiImplicitParam(name = "reportId", value = "报告ID")
    public R peopleanalyze_zy(@PathVariable String reportId) {
        return R.ok(this.ballCheckReportService.peopleanalyze_zy(reportId));
    }

    /**
     * 年龄分析 职业+健康
     *
     * @param reportId 查询条件
     * @return 所有数据
     */
    @GetMapping("/ageanalyze_zy/{reportId}")
    @ApiOperation(value = "年龄分布", notes = "年龄分布")
    @ApiImplicitParam(name = "reportId", value = "报告ID")
    public R ageanalyze_zy(@PathVariable String reportId) {
        return R.ok(this.ballCheckReportService.ageanalyze_zy(reportId));
    }

    /**
     * 日期分析 职业+健康
     *
     * @param reportId 查询条件
     * @return 所有数据
     */
    @GetMapping("/dateanalyze_zy/{reportId}")
    @ApiOperation(value = "日期分布", notes = "日期分布")
    @ApiImplicitParam(name = "reportId", value = "报告ID")
    public R dateanalyze_zy(@PathVariable String reportId) {
        return R.ok(this.ballCheckReportService.dateanalyze_zy(reportId));
    }

    /**
     * 危害因素人员分布
     *
     * @param reportId 查询条件
     * @return 所有数据
     */
    @GetMapping("/examanalyze_zy/{reportId}")
    @ApiOperation(value = "危害因素人员分布", notes = "危害因素人员分布")
    @ApiImplicitParam(name = "reportId", value = "报告ID")
    public R examanalyze_zy(@PathVariable String reportId) {
        return R.ok(this.ballCheckReportService.examanalyze_zy(reportId));
    }

    /**
     * 人员数据
     *
     * @param reportId 查询条件
     * @return 所有数据
     */
    @GetMapping("/getPersonnelData/{reportId}/{flag}")
    @ApiOperation(value = "人员数据", notes = "人员数据")
    @ApiImplicitParam(name = "reportId", value = "报告ID")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reportId", value = "报告ID"),
            @ApiImplicitParam(name = "flag", value = "结论（序列号")
    })
    public R getPersonnelData(@PathVariable String reportId, @PathVariable String flag) {
        return R.ok(this.ballCheckReportService.getPersonnelData(reportId, flag));
    }

    /**
     * 完成情况(健康+职业 )
     *
     * @param reportId 查询条件
     * @return 所有数据
     */
    @GetMapping("/finishanalyze_zy/{reportId}")
    @ApiOperation(value = "完成情况", notes = "完成情况(健康+职业)")
    @ApiImplicitParam(name = "reportId", value = "报告ID")
    public R<IPage<FxCompletion>> finishanalyze_zy(PageParamSimple pageParamSimple, @PathVariable String reportId) {
        PageParam<Report> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.ballCheckReportService.finishanalyze_zy(page,reportId));
    }

    /**
     * 复查情况
     *
     * @param reportId 查询条件
     * @return 所有数据
     */
    @GetMapping("/getReviewData/{reportId}")
    @ApiOperation(value = "复查情况", notes = "复查情况")
    @ApiImplicitParam(name = "reportId", value = "报告ID")
    public R getReviewData(@PathVariable String reportId) {
        return R.ok(this.ballCheckReportService.getReviewData(reportId));
    }

    /**
     * 保存所有图片(健康+职业)
     *
     * @param imgJkParam 查询条件
     * @return 所有数据
     */
    @PostMapping("/saveAllImgJk")
    @ApiOperation(value = "保存所有图片(健康+职业)", notes = "保存所有图片(健康+职业)")
    public R saveAllImgJk(@RequestBody ImgJkParam imgJkParam) throws IOException {
        return R.ok(this.ballCheckReportService.saveAllImgJk(imgJkParam));
    }

    /**
     * 生成样本分析
     *
     * @param ids，flag 查询条件
     * @return 所有数据
     */
    @GetMapping("/generateAnalyse/{ids}/{flag}")
    @ApiOperation(value = "生成样本分析", notes = "生成样本分析")
    @ApiImplicitParam(name = "reportId", value = "报告ID")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ID"),
            @ApiImplicitParam(name = "flag", value = "结论（序列号")
    })
    public R generateAnalyse(@PathVariable String ids, @PathVariable String flag) {
        return R.ok(this.ballCheckReportService.generateAnalyse(ids, flag));
    }


    /**
     * 修正
     *
     * @param ids
     * @return
     */
    @GetMapping("/undo/{ids}")
    @ApiOperation(value = "修正", notes = "撤销 减少一次打印次数")
    @ApiImplicitParam(name = "ids", value = "报告ID")
    public R undo(@PathVariable List<String> ids) {
        return R.toResult(this.ballCheckReportService.undo(ids));
    }


    /**
     * 主检审核
     *
     * @param id
     * @return
     */
    @PutMapping("/mainAudit")
    @ApiOperation(value = "主检审核", notes = "主检审核")
    @ApiImplicitParam(name = "id", value = "报告ID")
    public R mainAudit(String id) {
        return R.toResult(this.ballCheckReportService.mainAudit(id));
    }


    /**
     * 参检分析
     *
     * @param reportId
     * @param groupId
     * @return
     */
    @GetMapping("/examanalyze/{reportId}/{groupId}")
    @ApiOperation(value = "参检分析", notes = "参检分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reportId", value = "报告ID"),
            @ApiImplicitParam(name = "groupId", value = "分组id")
    })
    public R<List<FxItemscheck>> examanalyze(@PathVariable String reportId, @PathVariable String groupId) {
        return R.ok(this.ballCheckReportService.examanalyze(reportId, groupId));
    }


    /**
     * 检出统计
     *
     * @param reportId
     * @param groupId
     * @return
     */
    @GetMapping("/checkanalyze/{reportId}/{groupId}")
    @ApiOperation(value = "检出统计", notes = "检出统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reportId", value = "报告ID"),
            @ApiImplicitParam(name = "groupId", value = "分组id")
    })
    public R<IPage<CheckanalyzeVo>> checkanalyze(PageParamSimple pageParamSimple,@PathVariable String reportId,@PathVariable String groupId) {
        PageParam<Report> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<CheckanalyzeVo> list = ballCheckReportService.checkanalyze(page,reportId, groupId);
        return R.ok(list);
    }


    /**
     * 团体小结
     *
     * @param reportId
     * @param groupId
     * @return
     */
    @GetMapping("/groupanalyze/{reportId}/{groupId}")
    @ApiOperation(value = "团体小结", notes = "团体小结")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reportId", value = "报告ID"),
            @ApiImplicitParam(name = "groupId", value = "分组id")
    })
    public R<IPage<FxDetection>> groupanalyze(PageParamSimple pageParamSimple, @PathVariable String reportId, @PathVariable String groupId) {
        PageParam<FxDetection> page = mapperFacade.map(pageParamSimple, PageParam.class);
        PageParam<FxDetection> page1 = fxDetectionService.page(page, new QueryWrapper<FxDetection>()
                .orderByAsc("report_sort", "patientCode")
                .eq("sample_id", reportId)
                .ne("conclusion", "未见异常"));
        return R.ok(page1);
    }


    /**
     * 阳性分析
     *
     * @param reportId
     * @param groupId
     * @return
     */
    @GetMapping("/positiveanalyze/{reportId}/{groupId}")
    @ApiOperation(value = "阳性分析", notes = "阳性分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reportId", value = "报告ID"),
            @ApiImplicitParam(name = "groupId", value = "分组id")
    })
    public R<IPage<FxPositive>> positiveanalyze(PageParamSimple pageParamSimple, @PathVariable String reportId, @PathVariable String groupId) {
        PageParam<FxPositive> page = mapperFacade.map(pageParamSimple, PageParam.class);
        PageParam<FxPositive> page1 = fxPositiveService.page(page, new QueryWrapper<FxPositive>()
                .orderByAsc("patientcode").eq("sample_id", reportId));
        return R.ok(page1);
    }


    /**
     * 异常前十 检出统计
     *
     * @param reportId
     * @param groupId
     * @return
     */
    @GetMapping("/loadcheckanalyze/{reportId}/{groupId}")
    @ApiOperation(value = "异常前十 检出统计", notes = "异常前十 检出统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reportId", value = "报告ID"),
            @ApiImplicitParam(name = "groupId", value = "分组id")
    })
    public R loadcheckanalyze(@PathVariable String reportId, @PathVariable String groupId) {
        HashMap map = ballCheckReportService.loadcheckanalyze(reportId, groupId);
        return R.ok(map);
    }


    /**
     * 导出
     *
     * @param response
     * @param reportId
     */
    @Log(title = "阴性和阳性结果", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出阴性和阳性结果接口", notes = "导出阴性和阳性结果接口")
    @ApiImplicitParam(name = "reportId", value = "样本id")
    @PostMapping("/export")
    public void export(HttpServletResponse response, String reportId) {
        List<RAExportDataVo> list = ballCheckReportService.exportData(reportId);
        ExcelUtil<RAExportDataVo> util = new ExcelUtil<RAExportDataVo>(RAExportDataVo.class);
        util.exportExcel(response, list, "阴性和阳性结果");
    }

}

