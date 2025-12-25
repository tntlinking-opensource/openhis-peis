package com.center.medical.report.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Report;
import com.center.medical.report.bean.model.ReportUrl;
import com.center.medical.report.bean.param.PeispatientParam;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.report.bean.vo.PeispatientVo;
import com.center.medical.report.service.ProfessionReportService;
import com.center.medical.report.service.ReportService;
import com.center.medical.report.service.ReportUrlService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * QT体检者表(Peispatient)表控制层
 *
 * @author fjj
 * @since 2022-12-06 20:37:35
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "报告管理-报告打印-职业报告")
@RequestMapping("/report/profession")
public class ProfessionReportController extends BaseController {
    /**
     * 服务对象
     */
    private final ProfessionReportService professionReportService;
    private final MapperFacade mapperFacade;
    private final ReportUrlService reportUrlService;
    private final ReportService reportService;

    /**
     * 分页查询所有职业报告数据
     *
     * @param pageParamSimple 分页参数
     * @param param     查询条件
     * @return 所有数据
     */
    @GetMapping("/professionReport")
    @ApiOperation(value = "分页查询职业报告数据", notes = "分页查询职业报告数据")
    public R<IPage<PeispatientVo>> getPage(PageParamSimple pageParamSimple, PeispatientParam param) {
        PageParam<Peispatient> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.professionReportService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查QT体检者表详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<Peispatient> selectOne(@PathVariable String id) {
        return R.ok(this.professionReportService.getInfoById(id));
    }

    /**
     * 预览
     *
     * @param patientcode     查询条件
     * @return 所有数据
     */
    @GetMapping("/sample/{patientcode}")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "职业报告-预览", notes = "根据体验号预览信息")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R<List<ReportUrl>> sample(@PathVariable String patientcode) {
        List<ReportUrl> reportUrls = reportUrlService.getList(patientcode.trim(), 1, 1);
        List<ReportUrl> urls = new ArrayList<>();
        for (ReportUrl url : reportUrls) {
            urls.add(url);
        }
        Report rep = reportService.getInfoByPatientcode(patientcode, 1);
        String pdfUrl = "";
        if (rep != null && rep.getUrlPdf() != null) {
            pdfUrl = pdfUrl + rep.getUrlPdf();
            pdfUrl = pdfUrl.substring(pdfUrl.indexOf("f"));
        }
        return R.ok(urls, pdfUrl);
    }

    /**
     * 已打印
     *
     * @param patientcode     查询条件
     * @return 所有数据
     */
    @GetMapping("/backOut/{patientcode}/{dh}")
    @ApiOperation(value = "职业报告-已打印", notes = "根据体验号打印")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "dh", value = "类型：0.职业 1.健康")
    })
    public R backOut(@PathVariable(value = "patientcode") String patientcode,@PathVariable(value = "dh") Integer dh ){
        return R.toResult(this.professionReportService.append(patientcode.trim(),dh));
    }


    /**
     * 新增数据
     *
     * @param peispatient 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加QT体检者表")
    @Log(title = "QT体检者表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"peispatient.id"})
    public R insert(@RequestBody Peispatient peispatient) {
        return R.toResult(this.professionReportService.save(peispatient));
    }

    /**
     * 修改数据
     *
     * @param peispatient 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新QT体检者表")
    @Log(title = "QT体检者表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody Peispatient peispatient) {
        return R.toResult(this.professionReportService.updateById(peispatient));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除QT体检者表")
    @Log(title = "QT体检者表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.professionReportService.removeByIds(ids));
    }
}

