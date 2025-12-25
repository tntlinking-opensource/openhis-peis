package com.center.medical.statistics.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;

import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.statistics.bean.param.AnalyseInfoParam;
import com.center.medical.statistics.bean.param.EveryProjectParam;
import com.center.medical.statistics.bean.vo.AnalyseInfoVo;
import com.center.medical.statistics.bean.vo.EveryProjectVo;
import com.center.medical.statistics.service.EveryProjectService;
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
 * 每日体检项目统计(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-14 16:40:23
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "每日体检项目统计")
@RequestMapping("query/everyProject")
public class EveryProjectController extends BaseController {
    /**
     * 服务对象
     */
    private final EveryProjectService everyProjectService;
    private final MapperFacade mapperFacade;
    private final ISysDeptService sysDeptService;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param     查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询QT体检者表")
    public R<IPage<EveryProjectVo>> getPage(PageParamSimple pageParamSimple, EveryProjectParam param) {
        PageParam<EveryProjectVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.everyProjectService.getList(page, param));
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
     * 右侧体检者数据
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/analyseInfo")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "右侧体检者数据", notes = "右侧体检者数据")
    public R<IPage<AnalyseInfoVo>> analyseInfo(PageParamSimple pageParamSimple, AnalyseInfoParam param) {
        PageParam<AnalyseInfoVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.everyProjectService.analyseInfo(page, param));
    }




    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "每日项目体检量", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出统计", notes = "导出每日项目体检量")
    @PostMapping("/export")
    public void export(HttpServletResponse response, EveryProjectParam param) {
        List<EveryProjectVo> list = everyProjectService.exportData(param);
        ExcelUtil<EveryProjectVo> util = new ExcelUtil<EveryProjectVo>(EveryProjectVo.class);
        util.exportExcel(response, list, "每日项目体检量");
    }


    /**
     * 导出体检人员清单
     * @param response
     * @param param
     */
    @Log(title = "体检人员清单", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出人员清单", notes = "导出体检人员清单")
    @PostMapping("/exportInfo")
    public void exportInfo(HttpServletResponse response, AnalyseInfoParam param) {
        List<AnalyseInfoVo> list = everyProjectService.exportInfo(param);
        ExcelUtil<AnalyseInfoVo> util = new ExcelUtil<AnalyseInfoVo>(AnalyseInfoVo.class);
        util.exportExcel(response, list, "体检人员清单");
    }



}

