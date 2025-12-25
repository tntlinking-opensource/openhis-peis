package com.center.medical.outreach.pf.controller;

import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 第三方功能接口
 *
 * @author 路飞船长
 * @since 2024-11-06 15:14:06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "第三方功能接口")
@RequestMapping("/coo/customer/functions")
public class CooCustomerFuncController extends BaseController {

    /**
     * 博英心电图-重新获取报告
     * @param patientCode
     * @return
     */
    @GetMapping("/boying/againReportBack/{patientCode}")
    @ApiOperation(value = "博英心电图-重新获取报告", notes = "博英心电图-重新获取报告")
    public R<String> againReportBack(@PathVariable String patientCode) {
        return R.ok();
    }
}

