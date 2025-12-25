package com.center.medical.statistics.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.statistics.bean.param.FinanceCountParam;
import com.center.medical.statistics.bean.vo.FCTotalVo;
import com.center.medical.statistics.bean.vo.FinanceAmountVo;
import com.center.medical.statistics.bean.vo.FinanceCountVo;
import com.center.medical.statistics.service.FinanceCountService;
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
 * 体检费用统计-收费明细(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-14 16:40:23
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "体检费用统计-收费明细及费用合计")
@RequestMapping("statistics/financeCount")
public class FinanceCountController extends BaseController {
    /**
     * 服务对象
     */
    private final FinanceCountService financeCountService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param     查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询收费明细", notes = "分页查询收费明细")
    public R<IPage<FinanceCountVo>> getPage(PageParamSimple pageParamSimple, FinanceCountParam param) {
        PageParam<FinanceCountVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.financeCountService.getList(page, param));
    }




    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "财务收费统计", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出财务收费统计", notes = "导出财务收费统计")
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceCountParam param) {
        List<FinanceCountVo> list = financeCountService.exportData(param);
        ExcelUtil<FinanceCountVo> util = new ExcelUtil<FinanceCountVo>(FinanceCountVo.class);
        util.exportExcel(response, list, "财务收费统计");
    }


    /**
     * 分页查询费用合计
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getTotalList")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询费用合计", notes = "下方合计数据就不返了，就是各个合计相加")
    public R<IPage<FCTotalVo>> getTotalList(PageParamSimple pageParamSimple, FinanceCountParam param) {
        PageParam<FCTotalVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.financeCountService.getTotalList(page, param));
    }




    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "财务收费汇总", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出财务收费汇总", notes = "导出财务收费汇总")
    @PostMapping("/totalExport")
    public void totalExport(HttpServletResponse response, FinanceCountParam param) {
        List<FCTotalVo> list = financeCountService.totalExport(param);
        ExcelUtil<FCTotalVo> util = new ExcelUtil<FCTotalVo>(FCTotalVo.class);
        util.exportExcel(response, list, "财务收费汇总");
    }




    /**
     * 获取合计数据
     *
     * @param param     查询条件
     * @return 所有数据
     */
    @GetMapping("/financeCountAmount")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取合计数据", notes = "获取合计数据")
    public R<FinanceAmountVo> financeCountAmount(FinanceCountParam param) {
        return R.ok(this.financeCountService.financeCountAmount(param));
    }
}

