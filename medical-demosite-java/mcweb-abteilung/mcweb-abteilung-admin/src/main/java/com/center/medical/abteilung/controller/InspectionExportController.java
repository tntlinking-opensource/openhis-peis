package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.param.InspectionExportParam;
import com.center.medical.abteilung.bean.vo.InspectionExportVo;
import com.center.medical.abteilung.service.InspectionExportService;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;

import com.center.medical.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 检验数据导出参数
 *
 * @author ay
 * @since 2023-07-17 20:48:10
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "检验数据导出")
@RequestMapping("abteilung/inspectionExport")
public class InspectionExportController extends BaseController {
    /**
     * 服务对象
     */
    private final InspectionExportService inspectionExportService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param   查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询检验数据导出参数")
    public R<IPage<InspectionExportVo>> getPage(PageParamSimple pageParamSimple, InspectionExportParam param) {
        PageParam<InspectionExportVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.inspectionExportService.getPage(page, param));
    }



    /**
     * 导出业务类型
     * @param response
     * @param param
     */
    @Log(title = "检验数据导出", businessType = BusinessType.EXPORT)
    @ApiOperation(value = "导出", notes = "导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response, InspectionExportParam param) {
        List<InspectionExportVo> list = inspectionExportService.getExportData(param);
        ExcelUtil<InspectionExportVo> util = new ExcelUtil<InspectionExportVo>(InspectionExportVo.class);
        util.exportExcel(response, list, "检验数据导出");
    }

}

