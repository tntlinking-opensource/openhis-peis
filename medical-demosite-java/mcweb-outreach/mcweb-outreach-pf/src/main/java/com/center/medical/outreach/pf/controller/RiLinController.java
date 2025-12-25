package com.center.medical.outreach.pf.controller;

import com.center.medical.common.core.controller.BaseController;
import com.center.medical.report.service.ReportService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

/**
 * 体检预约-预约记录控制层
 *
 * @author ay
 * @since 2023-03-18 08:58:06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "接收瑞林萨尔报告")
@RequestMapping("open/api/Rilin")
public class RiLinController extends BaseController {
    /**
     * 服务对象
     */
    private final ReportService reportService;
    private final MapperFacade mapperFacade;

//    /**
//     * 接收报告
//     *
//     * @param param
//     */
//    @PostMapping("/receivingReports")
//    @ApiOperation(value = "接收报告", notes = "接收报告")
//    public R receivingReports(@Valid ReceivingReportsParam param) {
//        Boolean b = reportService.receivingReports(param);
//        return R.ok(b);
//    }


}

