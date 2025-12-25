package com.center.medical.statistics.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.statistics.bean.vo.EPTotalVo;
import com.center.medical.statistics.bean.vo.EveryPhysicalVo;
import com.center.medical.statistics.service.EveryPhysicalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 每日体检量统计(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-14 16:40:23
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "每日体检量统计")
@RequestMapping("statistics/everyPhysical")
public class EveryPhysicalController extends BaseController {
    /**
     * 服务对象
     */
    private final EveryPhysicalService everyPhysicalService;
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
    @ApiOperation(value = "分页查询", notes = "分页查询QT体检者表")
    public R<IPage<EveryPhysicalVo>> getPage(PageParamSimple pageParamSimple, BaseParam param) {
        PageParam<EveryPhysicalVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.everyPhysicalService.getList(page, param));
    }


    /**
     * 页面下方总计数据
     * @param param
     * @return
     */
    @GetMapping("/total")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "下方总计", notes = "页面下方总计数据")
    public R<EPTotalVo> total(BaseParam param) {
        return R.ok(this.everyPhysicalService.getTotal(param));
    }

    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "每日体检量统计", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "导出每日体检量统计")
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaseParam param) {
        List<EveryPhysicalVo> list = everyPhysicalService.exportData(param);
        ExcelUtil<EveryPhysicalVo> util = new ExcelUtil<EveryPhysicalVo>(EveryPhysicalVo.class);
        util.exportExcel(response, list, "每日体检量统计");
    }



}

