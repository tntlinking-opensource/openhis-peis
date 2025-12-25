package com.center.medical.statistics.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.statistics.bean.param.PacsQueryParam;
import com.center.medical.statistics.bean.vo.PacsQueryVo;
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
@Api(tags = "PACS查询统计-pacs科室工作量统计")
@RequestMapping("statistics/pacsQuery")
public class PacsQueryController extends BaseController {
    /**
     * 服务对象
     */
    private final PacsQueryService pacsQueryService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询PACS-体检者表")
    public R<IPage<PacsQueryVo>> getPage(PageParamSimple pageParamSimple, PacsQueryParam param) {
        PageParam<PacsQueryVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.pacsQueryService.getPage(page, param));
    }


    /**
     * 获取图表数据
     * @param param
     * @return
     */
    @GetMapping("/getTableData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取图表数据", notes = "获取图表数据")
    public R getTableData(PacsQueryParam param) {
        return R.ok(this.pacsQueryService.getTableData(param));
    }



    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "科室工作量统计", businessType = BusinessType.EXPORT)
    @ApiOperation(value = "导出", notes = "科室工作量统计")
    @PostMapping("/export")
    public void export(HttpServletResponse response, PacsQueryParam param) {
        List<PacsQueryVo> list = pacsQueryService.getExportData(param);
        ExcelUtil<PacsQueryVo> util = new ExcelUtil<PacsQueryVo>(PacsQueryVo.class);
        util.exportExcel(response, list, "科室工作量统计","科室工作量统计");
    }
}
