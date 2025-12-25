package com.center.medical.report.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Report;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.annotation.RepeatSubmit;
import com.center.medical.common.config.Domain;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.FileTypePath;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.ZipUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.service.BaseAttachmentConfigService;
import com.center.medical.report.bean.model.ReportUrl;
import com.center.medical.report.bean.param.BatchDownloadParam;
import com.center.medical.report.bean.param.GetReportUrlParam;
import com.center.medical.report.bean.param.PeispatientParam;
import com.center.medical.report.bean.vo.GetReportUrlVo;
import com.center.medical.report.bean.vo.PeispatientVo;
import com.center.medical.report.service.*;
import com.center.medical.report.utils.UrlFilesDownloadUtils;
import com.center.medical.system.bean.vo.AllDepartDataVo;
import com.center.medical.system.service.ISysConfigService;
import com.center.medical.system.service.ISysDeptService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 健康报告控制层
 *
 * @author makejava
 * @since 2022-12-06 20:37:35
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "报告管理-报告打印-健康报告")
@RequestMapping("/report/health")
public class HealthReportController extends BaseController {
    /**
     * 服务对象
     */
    private final HealthReportService healthReportService;
    private final MapperFacade mapperFacade;
    private final ReportUrlService reportUrlService;
    private final ReportService reportService;
    private final BaseAttachmentConfigService baseAttachmentConfigService;
    private final ISysDeptService sysDeptService;
    private final ISysConfigService iSysConfigService;
    private final IPersonalReportService iPersonalReportService;

    public static String YGIDS = "669";//乙肝五项收费项目id

    /**
     * 分页查询所有健康报告数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/getPage")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询健康报告数据")
    public R<IPage<PeispatientVo>> getPage(PageParamSimple pageParamSimple, PeispatientParam param) {
        PageParam<Peispatient> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.healthReportService.getPage(page, param));
    }

    /**
     * 预览
     *
     * @param patientcode 查询条件
     * @return 所有数据
     */
    @GetMapping("/sample/{patientcode}")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "健康报告-预览", notes = "根据体验号预览信息")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R<List<ReportUrl>> sample(@PathVariable String patientcode) {
        List<ReportUrl> reportUrls = reportUrlService.getList(patientcode.trim(), 1, 0);
        List<ReportUrl> urls = new ArrayList<>();
        for (ReportUrl url : reportUrls) {
            ReportUrl report = new ReportUrl();
            String str = "";
            if (url.getPdfUrl() != null) {
                str = url.getPdfUrl();
            } else {
                str = "系统未检索到报告生成记录!";
            }

            if (!StringUtils.isEmpty(str)) {
                report = url;
                report.setPdfUrl(str);
            }
            if (report != null) {
                urls.add(report);
            }
        }
        Report rep = reportService.getInfoByPatientcode(patientcode, 0);
        String pdfUrl = "";
        if (rep != null && rep.getUrlPdf() != null) {
            pdfUrl = pdfUrl + rep.getUrlPdf();
            pdfUrl = pdfUrl.substring(pdfUrl.indexOf("f"));
        }
        return R.ok(urls, pdfUrl);
    }

    /**
     * 批量打印
     *
     * @param patientcode 体检号
     * @return 所有数据
     */
    @GetMapping("/sampleBatch")
    @ApiOperation(value = "健康报告-批量打印", notes = "根据体验号批量打印")
    @ApiImplicitParam(name = "patientcode", value = "体检号集合")
    public R sampleBatch(@RequestParam List<String> patientcode) {
        List<Report> reports = reportService.list(new QueryWrapper<Report>()
                .in("patientcode", patientcode).eq("disease_health", 0));
        String reportUrl = "";
        String rootPath = FileTypePath.REAL_PATH;
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String dirPath = rootPath + "file\\temp\\report\\" + dateStr + "\\";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String tempName = UUID.randomUUID() + ".pdf";
        String newfile = dirPath + tempName;
        int s = reports.size();
        String[] files = new String[s];
        for (int i = 0; i < s; i++) {
            Report report = reports.get(i);
            if (ObjectUtils.isNotEmpty(report)) {
                files[i] = baseAttachmentConfigService.getReportRealPath(report);
            }
        }
        try {
            // TODO: 2023/2/23 生成pdf未完成
            //WordConvertPDF.mergePdfFilesForReport(files, newfile);
            reportUrl = "file\\temp\\report\\" + dateStr + "\\" + tempName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok(reportUrl);
    }

    /**
     * 已打印
     *
     * @param patientcode 查询条件
     * @return 所有数据
     */
    @GetMapping("/backOut/{patientcode}/{dh}")
    @ApiOperation(value = "健康报告-已打印", notes = "根据体验号打印")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "dh", value = "类型：0.职业 1.健康")
    })
    public R backOut(@PathVariable(value = "patientcode") String patientcode, @PathVariable(value = "dh") Integer dh) {
        return R.toResult(this.healthReportService.append(patientcode.trim(), dh));
    }

    /**
     * 下载PDF
     *
     * @param patientcode 体检号
     * @return 所有数据
     */
    @GetMapping("/downloadPdf/{patientcode}")
    @ApiOperation(value = "健康报告-下载PDF", notes = "根据体验号下载PDF")
    @ApiImplicitParam(name = "patientcode", value = "体检号，逗号分开")
    public R downloadPdf(@PathVariable(value = "patientcode") String patientcode) {
        return null;
    }

    /**
     * 电子版交接
     *
     * @param ids 体检号
     * @return 所有数据
     */
    @GetMapping("/electronicReportHandover/{ids}")
    @ApiOperation(value = "健康报告-电子版交接", notes = "电子版交接")
    @ApiImplicitParam(name = "ids", value = "ids，逗号分开")
    @Log(title = "健康报告电子版交接", businessType = BusinessType.UPDATE)
    public R electronicReportHandover(@PathVariable(value = "ids") String ids) {
        try {
            healthReportService.electronicReportHandover(ids);
            return R.ok(R.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return R.ok(R.FAIL);
        }

    }

    /**
     * 健康报告-隐私报告终审交接查询
     *
     * @param patientcodes 体检号,逗号隔开
     * @return 所有数据
     */
    @GetMapping("/searchZsjjCode/{patientcodes}")
    @ApiOperation(value = "健康报告-终审交接查询", notes = "健康报告-终审交接查询")
    @ApiImplicitParam(name = "patientcodes", value = "体检号，逗号分开")
    public R<List<Peispatient>> searchZsjjCode(@PathVariable(value = "patientcodes") String patientcodes) {
        return R.ok(this.healthReportService.searchZsjjCode(patientcodes));
    }

    /**
     * 健康报告-隐私报告终审交接保存
     *
     * @param ids id,逗号隔开
     * @return 所有数据
     */
    @GetMapping("/saveZsjj/{ids}")
    @ApiOperation(value = "健康报告-终审交接保存", notes = "健康报告-终审交接保存")
    @ApiImplicitParam(name = "ids", value = "ids，逗号分开")
    public R saveZsjj(@PathVariable(value = "ids") String ids) {
        return R.toResult(this.healthReportService.saveZsjj(ids));
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
        return R.ok(this.healthReportService.getInfoById(id));
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
        return R.toResult(this.healthReportService.save(peispatient));
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
        return R.toResult(this.healthReportService.updateById(peispatient));
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
        return R.toResult(this.healthReportService.removeByIds(ids));
    }


    /**
     * 获取所有部门
     *
     * @param key
     * @return
     */
    @GetMapping("/getAllDepartData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取所有部门", notes = "获取所有部门")
    @ApiImplicitParam(name = "key", value = "搜索的部门名或输入码")
    public R<List<AllDepartDataVo>> getAllDepartData(String key) {
        List<AllDepartDataVo> list = sysDeptService.getAllDepartData(key);
        return R.ok(list);
    }


    /**
     * 体检号
     *
     * @param patientcode
     * @return
     */
    @PostMapping("/createTiming")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "隐私报告生成", notes = "隐私报告生成")
    @ApiImplicitParam(name = "patientcode", value = "体检号集合")
    @Log(title = "隐私报告生成", businessType = BusinessType.INSERT)
    public R createTiming(@RequestBody List<String> patientcode) {
        Boolean b = iPersonalReportService.createTiming(patientcode);
        return R.ok(b);
    }





    /**
     * 批量下载
     *
     * @param param  参数
     * @return 所有数据
     */
    @PostMapping("/batchDownload")
    @Log(title = "批量下载报告", businessType = BusinessType.INSERT)
    @RepeatSubmit(interval = 10000, message = "10秒内只能点击一次！")
    @ApiOperation(value = "批量下载报告", notes = "批量下载报告")
    public void batchDownload(@RequestBody List<BatchDownloadParam> param, HttpServletResponse response) throws Exception {
        //线上线下地址
        Domain domain = iSysConfigService.getDomain();
        String prefix = ZhongkangConfig.isOnline()? domain.getRsPfDomain() : domain.getRsLcDomain();

        //下载
        String fileName = URLEncoder.encode("批量下载.zip","UTF-8");
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        if (param.size() <= 5) {
            // 小文件数量，使用同步下载
            UrlFilesDownloadUtils.downloadSynchronously(param, prefix, response);
        } else {
            // 大文件数量，使用异步并发下载
            UrlFilesDownloadUtils.downloadAsynchronously(param, prefix, response);
        }
    }





    /**
     * 批量下载
     *
     * @param param  参数
     * @return 所有数据
     */
    @PostMapping("/checkUrl")
    @Log(title = "检验url是否有效", businessType = BusinessType.INSERT)
    @RepeatSubmit(interval = 10000, message = "10秒内只能点击一次！")
    @ApiOperation(value = "检验url是否有效", notes = "检验url是否有效")
    public R checkUrl(@RequestBody List<BatchDownloadParam> param){
        //线上线下地址
        Domain domain = iSysConfigService.getDomain();
        String prefix = ZhongkangConfig.isOnline()? domain.getRsPfDomain() : domain.getRsLcDomain();
        List<String> errorMessage = new ArrayList<>();
        //检验url是否有效
        for (BatchDownloadParam zipstream : param) {
            String url = prefix + zipstream.getUrlPdf();
            if (!ZipUtils.isImageUrlExists(url)){
                errorMessage.add(zipstream.getNamingMethod() + "报告不存在！");
            }
        }
        if (CollectionUtil.isNotEmpty(errorMessage)){
            throw new ServiceException(String.join("", errorMessage));
        }
        return R.ok(Boolean.TRUE);
    }




    /**
     * 批量打印-获取报告url
     *
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/getReportUrl")
    @ApiOperation(value = "批量打印-获取报告信息", notes = "批量打印-获取报告信息")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R<GetReportUrlVo> getReportUrl(@Validated GetReportUrlParam param) {
        GetReportUrlVo vo = reportService.getReportUrl(param);
        return R.ok(vo);
    }



    /**
     * 批量打印-获取报告url
     *
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/getReportAddress")
    @ApiOperation(value = "获取报告地址", notes = "获取报告地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcodes", value = "体检号,多个体检号用逗号分割"),
            @ApiImplicitParam(name = "diseaseHealth", value = "职业1/健康0")
    })
    public R<List<String>> getReportAddress(@RequestParam(required = true) String patientcodes,
                                            @RequestParam(required = true) Integer diseaseHealth) {
        List<String> codelist = Arrays.asList(patientcodes.split(","));
        List<String> vo = reportService.getReportAddress(codelist,diseaseHealth);
        return R.ok(vo);
    }

}

