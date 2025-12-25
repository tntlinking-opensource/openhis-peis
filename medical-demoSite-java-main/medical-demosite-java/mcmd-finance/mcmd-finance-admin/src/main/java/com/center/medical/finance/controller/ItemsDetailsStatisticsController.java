package com.center.medical.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.finance.bean.param.ItemsDetailsStatisticsParam;
import com.center.medical.finance.bean.vo.ItemsDetailsStatisticsVo;
import com.center.medical.finance.service.ItemsDetailsStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 项目明细统计表控制层
 *
 * @author ay
 * @since 2023-04-08 09:31:50
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "项目明细统计")
@RequestMapping("query/itemsDetailsStatistics")
public class ItemsDetailsStatisticsController extends BaseController {
    /**
     * 服务对象
     */
    private final ItemsDetailsStatisticsService itemsDetailsStatisticsService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<ItemsDetailsStatisticsVo>> getPage(PageParamSimple pageParamSimple, ItemsDetailsStatisticsParam param) {
        PageParam<ItemsDetailsStatisticsVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.itemsDetailsStatisticsService.getList(page, param));
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @PostMapping("/export")
    @ApiOperation(value = "导出", notes = "导出项目明细统计")
    @Log(title = "项目明细统计", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, ItemsDetailsStatisticsParam param) {
        List<ItemsDetailsStatisticsVo> list = itemsDetailsStatisticsService.getExportData(param);
        ExcelUtil<ItemsDetailsStatisticsVo> util = new ExcelUtil<ItemsDetailsStatisticsVo>(ItemsDetailsStatisticsVo.class);
        util.exportExcel(response, list, "项目明细统计","项目明细统计");
    }



}

