package com.center.medical.statistics.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.statistics.bean.param.AnalyseTestParam;
import com.center.medical.statistics.bean.vo.AnalysePacsVo;
import com.center.medical.statistics.bean.vo.ExportTotalVo;
import com.center.medical.statistics.service.DivisionDoctorService;
import com.center.medical.statistics.service.PacsQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * PACS-体检者表(PacsPeispatient)接口
 *
 * @author ay
 * @since 2023-09-16 16:37:07
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "PACS查询统计-PACS科室医生工作量统计")
@RequestMapping("statistics/pacsDoctorQuery")
public class PacsDoctorQueryController extends BaseController {
    /**
     * 服务对象
     */
    private final PacsQueryService pacsQueryService;

    private final DivisionDoctorService divisionDoctorService;
    private final MapperFacade mapperFacade;

    /**
     * pacs科室医生工作量统计
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getWorkData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "pacs科室医生工作量统计", notes = "pacs科室医生工作量统计")
    public R<IPage<AnalysePacsVo>> analysePacs(PageParamSimple pageParamSimple, AnalyseTestParam param) {
        // TODO: wait PACS查询表可能有问题，不用这些数据库了
        PageParam<AnalysePacsVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.divisionDoctorService.analysePacs(page, param));
    }


    /**
     * pacs科室医生工作量统计
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/analysePacsTotal")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "pacs科室医生工作量统计总计", notes = "pacs科室医生工作量统计总计")
    public R<IPage<AnalysePacsVo>> analysePacsTotal(PageParamSimple pageParamSimple, AnalyseTestParam param) {
        // TODO: wait PACS查询表可能有问题，不用这些数据库了
        PageParam<AnalysePacsVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.divisionDoctorService.analysePacsTotal(page, param));
    }


    /**
     * 获取图表数据
     * @param param
     * @return
     */
    @GetMapping("/getTableData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取图表数据", notes = "获取图表数据")
    public R getTableData(AnalyseTestParam param) {
        return R.ok(this.pacsQueryService.getPacsDoctorTableData(param));
    }



    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "导出", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出工作量统计", notes = "导出工作量统计")
    @PostMapping("/exportWorkData")
    public void export(HttpServletResponse response, AnalyseTestParam param) {
        List<AnalysePacsVo> list = pacsQueryService.exportWorkData(param);
        ExcelUtil<AnalysePacsVo> util = new ExcelUtil<AnalysePacsVo>(AnalysePacsVo.class);
        util.exportExcel(response, list, "pacs科室医生工作量统计","pacs科室医生工作量统计");
    }




    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "导出", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出工作量统计总计", notes = "导出工作量统计总计")
    @PostMapping("/exportTotal")
    public void exportTotal(HttpServletResponse response, AnalyseTestParam param) {
        List<ExportTotalVo> list = pacsQueryService.exportTotal(param);
        ExcelUtil<ExportTotalVo> util = new ExcelUtil<ExportTotalVo>(ExportTotalVo.class);
        util.exportExcel(response, list, "pacs科室医生工作量统计总计","pacs科室医生工作量统计总计");
    }

}
