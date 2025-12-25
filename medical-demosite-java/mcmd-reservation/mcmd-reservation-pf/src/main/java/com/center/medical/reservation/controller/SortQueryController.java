package com.center.medical.reservation.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.reservation.bean.param.SortQueryParam;
import com.center.medical.reservation.bean.vo.SortQueryVo;
import com.center.medical.reservation.service.SortQueryService;
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
 * 体检预约记录(Reservation)接口
 *
 * @author ay
 * @since 2024-01-18 17:12:03
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "项目预约明细")
@RequestMapping("/reservation/sortQuery")
public class SortQueryController extends BaseController {
    /**
     * 服务对象
     */
    private final SortQueryService sortQueryService;
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
    @ApiOperation(value = "分页查询", notes = "分页查询体检预约记录")
    public R<IPage<SortQueryVo>> getPage(PageParamSimple pageParamSimple, SortQueryParam param) {
        PageParam<SortQueryVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sortQueryService.getPage(page, param));
    }




    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "导出项目预约明细", businessType = BusinessType.EXPORT)
    @ApiOperation(value = "导出项目预约明细", notes = "导出项目预约明细")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SortQueryParam param) {
        List<SortQueryVo> list = sortQueryService.exportData(param);
        int i = 1;
        for (SortQueryVo sortQueryVo : list) {
            sortQueryVo.setRownum(i++);
        }
        ExcelUtil<SortQueryVo> util = new ExcelUtil<SortQueryVo>(SortQueryVo.class);
        util.exportExcel(response, list, "项目预约明细");
    }

}
