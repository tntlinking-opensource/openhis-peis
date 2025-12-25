package com.center.medical.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.finance.bean.param.TDPageParam;
import com.center.medical.finance.bean.vo.TDPageVo;
import com.center.medical.finance.service.TallyDetailService;
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
 * 记账管理-记账结算明细(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-03-31 14:27:31
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "记账管理-记账结算明细")
@RequestMapping("finance/tallyDetail")
public class TallyDetailController extends BaseController {
    /**
     * 服务对象
     */
    private final TallyDetailService tallyDetailService;
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
    @ApiOperation(value = "分页查询", notes = "合计heji字段，就是moneyamountpaid相加")
    public R<IPage<TDPageVo>> getPage(PageParamSimple pageParamSimple, TDPageParam param) {
        PageParam<TDPageVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.tallyDetailService.getList(page, param));
    }

    /**
     * 导出记帐结算明细
     *
     * @param response
     * @param param
     */
    @Log(title = "记帐结算明细", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出记帐结算明细", notes = "导出记帐结算明细")
    @PostMapping("/export")
    public void export(HttpServletResponse response, TDPageParam param) {
        List<TDPageVo> list = tallyDetailService.getExportData(param);
        ExcelUtil<TDPageVo> util = new ExcelUtil<TDPageVo>(TDPageVo.class);
        util.exportExcel(response, list, "记帐结算明细");
    }
}

