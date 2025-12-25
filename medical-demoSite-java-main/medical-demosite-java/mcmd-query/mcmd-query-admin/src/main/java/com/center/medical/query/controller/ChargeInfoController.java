package com.center.medical.query.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.query.bean.param.ChargeInfoParam;
import com.center.medical.query.bean.vo.ChargeInfoPageVo;
import com.center.medical.query.bean.vo.FinanceAmountVo;
import com.center.medical.query.service.ChargeInfoService;
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
 * QT体检者表(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-08 09:31:50
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "收费信息查询")
@RequestMapping("query/chargeInfo")
public class ChargeInfoController extends BaseController {
    /**
     * 服务对象
     */
    private final ChargeInfoService chargeInfoService;
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
    @ApiOperation(value = "分页查询", notes = "分页查询QT体检者表")
    public R<IPage<ChargeInfoPageVo>> getPage(PageParamSimple pageParamSimple, ChargeInfoParam param) {
        PageParam<ChargeInfoPageVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.chargeInfoService.getList(page, param));
    }


    /**
     * 导出收费信息查询
     *
     * @param response
     * @param param
     */
    @Log(title = "收费信息查询", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "导出收费信息查询")
    @PostMapping("/export")
    public void export(HttpServletResponse response, ChargeInfoParam param) {
        List<ChargeInfoPageVo> list = chargeInfoService.getExportData(param);
        ExcelUtil<ChargeInfoPageVo> util = new ExcelUtil<ChargeInfoPageVo>(ChargeInfoPageVo.class);
        util.exportExcel(response, list, "收费信息查询");
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
    public R<FinanceAmountVo> financeCountAmount(ChargeInfoParam param) {
        return R.ok(this.chargeInfoService.financeCountAmount(param));
    }
}

