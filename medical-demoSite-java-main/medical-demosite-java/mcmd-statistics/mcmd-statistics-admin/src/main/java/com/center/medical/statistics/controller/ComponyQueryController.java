package com.center.medical.statistics.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.statistics.bean.param.ComponyQueryParam;
import com.center.medical.statistics.bean.vo.ComponyQueryVo;
import com.center.medical.statistics.service.SummaryQueryService;
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
 * 职业健康检查结果汇总表(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-14 16:40:23
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "职业检查统计-职业健康检查结果汇总表 (按单位)")
@RequestMapping("statistics/componyQuery")
public class ComponyQueryController extends BaseController {
    /**
     * 服务对象
     */
    private final SummaryQueryService summaryQueryService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询职业健康检查结果汇总表 (按单位)")
    public R<IPage<ComponyQueryVo>> getPage(PageParamSimple pageParamSimple, ComponyQueryParam param) {
        PageParam<ComponyQueryVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.summaryQueryService.getComponyQuery(page, param));
    }

    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "单位职业健康检查结果附表（按用人单位统计）", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "导出单位职业健康检查结果附表（按用人单位统计）")
    @PostMapping("/exportComponyQuery")
    public void export(HttpServletResponse response, ComponyQueryParam param) {
        List<ComponyQueryVo> list = summaryQueryService.exportComponyQuery(param);
        ExcelUtil<ComponyQueryVo> util = new ExcelUtil<ComponyQueryVo>(ComponyQueryVo.class);
        util.exportExcel(response, list, "单位职业健康检查结果附表（按用人单位统计）", "单位职业健康检查结果附表（按用人单位统计）");
    }

}

