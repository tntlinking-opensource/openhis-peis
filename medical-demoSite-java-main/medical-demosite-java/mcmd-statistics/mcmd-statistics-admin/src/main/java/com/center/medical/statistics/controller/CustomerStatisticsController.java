package com.center.medical.statistics.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.statistics.bean.param.CustomerStatisticsParam;
import com.center.medical.statistics.bean.param.PhysicalExaminerParam;
import com.center.medical.statistics.bean.vo.CustomerStatisticsVo;
import com.center.medical.statistics.bean.vo.PhysicalExaminerVo;
import com.center.medical.statistics.service.CustomerStatisticsService;
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
 * 来检客户统计(Peispatient)接口
 *
 * @author ay
 * @since 2023-12-18 15:52:57
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "来检客户统计")
@RequestMapping("statistics/customerStatistics")
public class CustomerStatisticsController extends BaseController {
    /**
     * 服务对象
     */
    private final CustomerStatisticsService customerStatisticsService;
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
    @ApiOperation(value = "获取公司及订单(左边分页)", notes = "获取公司及订单(左边分页)")
    public R<IPage<CustomerStatisticsVo>> getPage(PageParamSimple pageParamSimple, CustomerStatisticsParam param) {
        PageParam<CustomerStatisticsVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.customerStatisticsService.getPage(page, param));
    }


    /**
     * 获取体检者
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getPhysicalExaminer")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取体检者(右边分页)", notes = "获取体检者(右边分页)")
    public R<IPage<PhysicalExaminerVo>> getPhysicalExaminer(PageParamSimple pageParamSimple, PhysicalExaminerParam param) {
        PageParam<PhysicalExaminerVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.customerStatisticsService.getPhysicalExaminer(page, param));
    }




    /**
     * 导出订单信息
     *
     * @param response
     * @param param
     */
    @Log(title = "导出订单信息", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出订单信息", notes = "导出订单信息")
    @PostMapping("/exportOrg")
    public void exportOrg(HttpServletResponse response, CustomerStatisticsParam param){
        List<CustomerStatisticsVo> list = customerStatisticsService.exportOrg(param);
        ExcelUtil<CustomerStatisticsVo> util = new ExcelUtil<CustomerStatisticsVo>(CustomerStatisticsVo.class);
        util.exportExcel(response, list, "导出订单信息");
    }





    /**
     * 导出人员信息
     *
     * @param response
     * @param param
     */
    @Log(title = "导出人员信息", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出人员信息", notes = "导出人员信息")
    @PostMapping("/exportPer")
    public void exportPer(HttpServletResponse response, PhysicalExaminerParam param){
        List<PhysicalExaminerVo> list = customerStatisticsService.exportPer(param);
        ExcelUtil<PhysicalExaminerVo> util = new ExcelUtil<PhysicalExaminerVo>(PhysicalExaminerVo.class);
        util.exportExcel(response, list, "导出人员信息");
    }
}
