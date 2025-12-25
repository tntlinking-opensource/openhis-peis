package com.center.medical.statistics.controller;

import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.statistics.bean.param.ComponyQueryParam;
import com.center.medical.statistics.bean.vo.HarmQueryVo;
import com.center.medical.statistics.service.SummaryQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * QT体检者表(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-14 16:40:23
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "职业检查统计-职业健康检查结果汇总表 (按危害因素)")
@RequestMapping("statistics/harmQuery")
public class HarmQueryController extends BaseController {
    /**
     * 服务对象
     */
    private final SummaryQueryService summaryQueryService;
    private final MapperFacade mapperFacade;

    /**
     * 查询数据
     *
     * @return 所有数据
     */
    @GetMapping("/getListData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "查询数据", notes = "查询职业健康检查结果汇总表 (按危害因素)")
    public R<List<HarmQueryVo>> getPage(ComponyQueryParam param) {
        List<HarmQueryVo> list = summaryQueryService.getHarmQuery(param);
        return R.ok(list);
    }



    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "单位职业健康检查结果附表", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "导出单位职业健康检查结果附表(按毒物类别统计)")
    @PostMapping("/export")
    public void export(HttpServletResponse response, ComponyQueryParam param) {
        List<HarmQueryVo> list = summaryQueryService.getHarmQuery(param);
        ExcelUtil<HarmQueryVo> util = new ExcelUtil<HarmQueryVo>(HarmQueryVo.class);
        util.exportExcel(response, list, "单位职业健康检查结果附表(按毒物类别统计)");
    }

}

