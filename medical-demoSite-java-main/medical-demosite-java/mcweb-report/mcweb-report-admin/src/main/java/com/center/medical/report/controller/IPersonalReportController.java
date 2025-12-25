package com.center.medical.report.controller;

import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.report.bean.param.IReportParam;
import com.center.medical.report.service.IPersonalReportService;
import com.center.medical.service.ReportContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康报告-生成报告(Report)表控制层
 *
 * @author ay
 * @since 2023-04-21 16:55:19
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "个检-健康职业报告")
@RequestMapping("report/iPersonalReport")
public class IPersonalReportController extends BaseController {
    /**
     * 服务对象
     */
    private final IPersonalReportService iPersonalReportService;
    private final MapperFacade mapperFacade;
    private final ReportContentService reportContentService;

    /**
     * 分页查询所有数据
     *
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/create")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @Log(title = "速成或生成检验报告", businessType = BusinessType.INSERT)
    @ApiOperation(value = "速成或生成检验报告", notes = "速成或生成检验报告")
    public R<Boolean> create(IReportParam param) {
        param.setUsername(SecurityUtils.getUsername());
        Boolean b = iPersonalReportService.create(param);
        return R.ok(b);
    }





    /**
     * 跳过校验生成报告
     *
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/skipCheckCreateReport")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "跳过校验生成报告", notes = "跳过校验生成报告")
    public R<Boolean> skipCheckCreateReport(IReportParam param) {
        Boolean b = iPersonalReportService.createOldReport(param);
        return R.ok(b);
    }



}

