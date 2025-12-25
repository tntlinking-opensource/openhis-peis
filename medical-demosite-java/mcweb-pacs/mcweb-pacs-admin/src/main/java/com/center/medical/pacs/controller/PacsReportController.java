package com.center.medical.pacs.controller;

import com.alibaba.fastjson.JSON;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.pacs.bean.param.CreateRadParam;
import com.center.medical.pacs.service.PacsReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

/**
 * PACS报告数据(PacsResult)表控制层
 *
 * @author ay
 * @since 2023-05-19 15:22:03
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "PACS报告数据")
@RequestMapping("/pacs/pacsReport")
public class PacsReportController extends BaseController {
    /**
     * 服务对象
     */
    private final PacsReportService pacsReportService;
    private final MapperFacade mapperFacade;

    /**
     * 生成放射科室报告
     *
     * @param param 查询条件
     * @return 所有数据
     */
    @PostMapping("/createRad")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "生成放射科室报告", notes = "生成放射科室报告")
    public R createRad(@RequestBody CreateRadParam param ) {
        Boolean b = pacsReportService.createRad(param);
        return R.ok(b);
    }


    /**
     * 选择技术方法
     * @return
     */
    @GetMapping("/tech")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "选择技术方法", notes = "选择技术方法")
    public R tech() {
        return R.ok(JSON.parse(Constants.TECH));
    }
}

