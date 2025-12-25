package com.center.medical.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.finance.bean.param.SDPageParam;
import com.center.medical.finance.bean.vo.SDPageVo;
import com.center.medical.finance.service.SpendDetailService;
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
 * 体检卡管理-卡消费明细(Card)表控制层
 *
 * @author ay
 * @since 2023-03-30 18:47:30
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "体检卡管理-卡消费明细")
@RequestMapping("finance/spendDetail")
public class SpendDetailController extends BaseController {
    /**
     * 服务对象
     */
    private final SpendDetailService spendDetailService;
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
    @ApiOperation(value = "分页查询", notes = "分页查询体检卡")
    public R<IPage<SDPageVo>> getPage(PageParamSimple pageParamSimple, SDPageParam param) {
        PageParam<SDPageVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.spendDetailService.getList(page, param));
    }

    /**
     * 导出体检卡消费明细
     *
     * @param response
     * @param param
     */
    @Log(title = "体检卡消费明细", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出体检卡消费明细", notes = "导出体检卡消费明细")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SDPageParam param) {
        List<SDPageVo> list = spendDetailService.getExportData(param);
        ExcelUtil<SDPageVo> util = new ExcelUtil<SDPageVo>(SDPageVo.class);
        util.exportExcel(response, list, "体检卡消费明细");
    }
}

