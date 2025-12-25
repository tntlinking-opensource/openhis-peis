package com.center.medical.statistics.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.sellcrm.bean.vo.AllOrgVo;
import com.center.medical.sellcrm.service.SellcustomerService;
import com.center.medical.statistics.bean.param.PositiveQueryParam;
import com.center.medical.statistics.bean.vo.PositiveQueryVo;
import com.center.medical.statistics.service.PositiveQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 职业健康检查结果结论附表(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-14 16:40:23
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "职业检查统计-职业健康检查结果结论附表")
@RequestMapping("statistics/positiveQuery")
public class PositiveQueryController extends BaseController {
    /**
     * 服务对象
     */
    private final PositiveQueryService positiveQueryService;
    private final MapperFacade mapperFacade;
    private final SellcustomerService sellcustomerService;

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
    public R<IPage<PositiveQueryVo>> getPage(PageParamSimple pageParamSimple, PositiveQueryParam param) {
        PageParam<PositiveQueryVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.positiveQueryService.getList(page, param));
    }


    /**
     * 获取所有客户
     *
     * @param pageParamSimple
     * @param key
     * @return
     */
    @GetMapping("/getAllOrg")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取所有客户", notes = "获取所有客户")
    @ApiImplicitParam(name = "key", value = "客户单位名称或者输入码")
    public R<IPage<AllOrgVo>> getAllOrg(PageParamSimple pageParamSimple, @RequestParam(name = "key", defaultValue = "") String key) {
        PageParam<AllOrgVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sellcustomerService.getAllOrg(page, key));
    }




    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "单位职业健康检查结果附表", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "导出单位职业健康检查结果附表")
    @PostMapping("/export")
    public void export(HttpServletResponse response, PositiveQueryParam param) {
        List<PositiveQueryVo> list = positiveQueryService.getExportData(param);
        ExcelUtil<PositiveQueryVo> util = new ExcelUtil<PositiveQueryVo>(PositiveQueryVo.class);
        util.exportExcel(response, list, "单位职业健康检查结果附表");
    }



}

