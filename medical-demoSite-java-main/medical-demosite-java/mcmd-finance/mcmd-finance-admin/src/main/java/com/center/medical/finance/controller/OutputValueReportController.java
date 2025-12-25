package com.center.medical.finance.controller;

import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.finance.bean.dto.PhysicalExaminationDto;
import com.center.medical.finance.service.OutputValueReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 财务报表-产值报表(Createorder)表控制层
 *
 * @author ay
 * @since 2023-05-15 09:37:34
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "财务报表-产值报表")
@RequestMapping("finance/outputValueReport")
public class OutputValueReportController extends BaseController {
    /**
     * 服务对象
     */
    private final OutputValueReportService outputValueReportService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping("/getList")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取产值报表数据", notes = "获取产值报表数据")
    public R<List<PhysicalExaminationDto>> getList(BaseParam param) {
        List<PhysicalExaminationDto> list = outputValueReportService.getList(param);
        return R.ok(list);
    }



    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping("/getInspectOutputValue")
    @ApiOperation(value = "获取体检产值", notes = "获取体检产值")
    public R<PhysicalExaminationDto> getInspectOutputValue(BaseParam param) {
        PhysicalExaminationDto dto = outputValueReportService.getInspectOutputValue(param);
        return R.ok(dto);
    }



    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping("/getVaccinesOutputValue")
    @ApiOperation(value = "获取疫苗产值", notes = "获取疫苗产值")
    public R<PhysicalExaminationDto> getVaccinesOutputValue(BaseParam param) {
        PhysicalExaminationDto dto = outputValueReportService.getVaccinesOutputValue(param);
        return R.ok(dto);
    }
}

