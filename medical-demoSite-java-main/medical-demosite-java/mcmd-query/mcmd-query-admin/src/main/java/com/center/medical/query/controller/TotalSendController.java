package com.center.medical.query.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.query.bean.param.TotalSendParam;
import com.center.medical.query.bean.vo.TotalSendVo;
import com.center.medical.query.service.TotalSendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 外送项目查询(OutsideMain)表控制层
 *
 * @author ay
 * @since 2023-04-12 11:59:11
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "外送项目查询")
@RequestMapping("query/totalSend")
public class TotalSendController extends BaseController {
    /**
     * 服务对象
     */
    private final TotalSendService totalSendService;
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
    @ApiOperation(value = "分页查询", notes = "分页查询KS外送登记结果主表")
    public R<IPage<TotalSendVo>> getPage(PageParamSimple pageParamSimple, TotalSendParam param) {
        PageParam<TotalSendVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.totalSendService.getList(page, param));
    }


    /**
     * 获取合计金额
     * @param param
     * @return
     */
    @GetMapping("/amountTo")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取合计金额", notes = "分页下面的合计")
    public R amountTo(TotalSendParam param) {
        String amountTo = totalSendService.amountTo(param);
        return R.ok(amountTo);
    }




    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @PostMapping("/export")
    @ApiOperation(value = "导出", notes = "导出外送统计")
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @Log(title = "外送统计", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, TotalSendParam param) {
        List<TotalSendVo> list = totalSendService.getExportData(param);
        ExcelUtil<TotalSendVo> util = new ExcelUtil<TotalSendVo>(TotalSendVo.class);
        util.exportExcel(response, list, "外送统计");
    }
}

