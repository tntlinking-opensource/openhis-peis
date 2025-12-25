package com.center.medical.enterprise.controller.main;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.enterprise.bean.dto.PageParamSimple;
import com.center.medical.enterprise.bean.model.Report;
import com.center.medical.enterprise.bean.param.OrderListDataParam;
import com.center.medical.enterprise.bean.param.ReportListDataParam;
import com.center.medical.enterprise.bean.vo.*;
import com.center.medical.enterprise.common.core.domain.R;
import com.center.medical.enterprise.common.util.PageParam;
import com.center.medical.enterprise.common.utils.poi.ExcelUtil;
import com.center.medical.enterprise.service.ReportService;
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
 * 体检报告(MdReport)接口
 *
 * @author ay
 * @since 2023-07-17 20:48:16
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "体检报告")
@RequestMapping("sell/report")
public class ReportController {
    /**
     * 服务对象
     */
    private final ReportService reportService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param        查询条件
     * @return 所有数据
     */
    @GetMapping("/getListData")
    @ApiOperation(value = "分页查询", notes = "分页查询BG报告主表")
    public R<IPage<ReportListDataVo>> getPage(PageParamSimple pageParamSimple, ReportListDataParam param) {
        PageParam<ReportListDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reportService.getPage(page, param));
    }


    /**
     * 健康结论导出
     * @param response
     * @param param
     */
    @ApiOperation(value = "健康结论导出", notes = "健康结论导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response, ReportListDataParam param) {
        log.info("健康结论导出，导出参数：{}", param);
        List<ReportExportVo> list = reportService.getExportData(param);
        ExcelUtil<ReportExportVo> util = new ExcelUtil<ReportExportVo>(ReportExportVo.class);
        util.exportExcel(response, list, "体检结果导出","体检结果导出");
    }



    /**
     * 健康结论导出
     * @param response
     * @param param
     */
    @ApiOperation(value = "职业结论导出", notes = "职业结论导出")
    @PostMapping("/exportZy")
    public void exportZy(HttpServletResponse response, ReportListDataParam param) {
        log.info("职业结论导出，导出参数：{}", param);
        List<ReportExportZyVo> list = reportService.getExportZyData(param);
        ExcelUtil<ReportExportZyVo> util = new ExcelUtil<ReportExportZyVo>(ReportExportZyVo.class);
        util.exportExcel(response, list, "体检结果导出","体检结果导出");
    }




    /**
     * 分页查询所有数据
     *
     * @param id        查询条件
     * @return 所有数据
     */
    @GetMapping("/getCompareData")
    @ApiOperation(value = "对比报告数据", notes = "对比报告数据")
    public R<List<CompareDataVo>> getCompareData(String id) {
        List<CompareDataVo> vo = reportService.getCompareData(id);
        return R.ok(vo);
    }



    /**
     * 获取订单信息
     *
     * @param pageParamSimple 分页参数
     * @param param        查询条件
     * @return 所有数据
     */
    @GetMapping("/getOrderListData")
    @ApiOperation(value = "获取订单信息", notes = "获取订单信息")
    public R<IPage<OrderListDataVo>> getOrderListData(PageParamSimple pageParamSimple, OrderListDataParam param) {
        PageParam<OrderListDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reportService.getOrderListData(page, param));
    }
}

