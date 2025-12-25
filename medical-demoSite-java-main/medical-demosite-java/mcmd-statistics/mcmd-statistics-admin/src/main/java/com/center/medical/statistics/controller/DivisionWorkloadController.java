package com.center.medical.statistics.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.statistics.bean.param.DivisionWorkParam;
import com.center.medical.statistics.bean.vo.DivisionWorkVo;
import com.center.medical.statistics.service.DivisionWorkloadService;
import com.center.medical.system.bean.vo.AllDepartDataVo;
import com.center.medical.system.service.ISysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 科室工作量(Peispatientfeeitem)表控制层
 *
 * @author ay
 * @since 2023-04-18 19:47:26
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "工作量统计-科室工作量")
@RequestMapping("statistics/divisionWorkload")
public class DivisionWorkloadController extends BaseController {
    /**
     * 服务对象
     */
    private final DivisionWorkloadService divisionWorkloadService;
    private final MapperFacade mapperFacade;
    private final ISysDeptService sysDeptService;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple    分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询体检者收费项目表")
    public R<IPage<DivisionWorkVo>> getPage(PageParamSimple pageParamSimple, DivisionWorkParam param) {
        PageParam<DivisionWorkVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.divisionWorkloadService.getList(page, param));
    }


    /**
     * 获取科室
     * @return
     */
    @GetMapping("/ks")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取科室", notes = "获取科室")
    public R<List<AllDepartDataVo>> ks() {
        List<AllDepartDataVo> list = sysDeptService.getAllDepartData(null);
        return R.ok(list);
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "科室工作量统计", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "导出科室工作量统计")
    @PostMapping("/export")
    public void export(HttpServletResponse response, DivisionWorkParam param) {
        List<DivisionWorkVo> list = divisionWorkloadService.exportData(param);
        ExcelUtil<DivisionWorkVo> util = new ExcelUtil<DivisionWorkVo>(DivisionWorkVo.class);
        util.exportExcel(response, list, "科室工作量统计");
    }

}

