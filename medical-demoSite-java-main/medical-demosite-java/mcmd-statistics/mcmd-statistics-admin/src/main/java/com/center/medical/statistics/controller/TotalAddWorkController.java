package com.center.medical.statistics.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.statistics.bean.param.TotalAddWorkParam;
import com.center.medical.statistics.bean.vo.TotalAddWorkVo;
import com.center.medical.statistics.service.TotalAddWorkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 体检者收费项目表(Peispatientfeeitem)表控制层
 *
 * @author ay
 * @since 2023-04-19 19:06:09
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "工作量统计-加项工作量")
@RequestMapping("statistics/totalAddWork")
public class TotalAddWorkController extends BaseController {
    /**
     * 服务对象
     */
    private final TotalAddWorkService totalAddWorkService;
    private final MapperFacade mapperFacade;

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
    public R<IPage<TotalAddWorkVo>> getPage(PageParamSimple pageParamSimple, TotalAddWorkParam param) {
        PageParam<TotalAddWorkVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.totalAddWorkService.getList(page, param));
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "加项统计", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "导出加项统计")
    @PostMapping("/export")
    public void export(HttpServletResponse response, TotalAddWorkParam param) {
        List<TotalAddWorkVo> list = totalAddWorkService.getExportData(param);
        ExcelUtil<TotalAddWorkVo> util = new ExcelUtil<TotalAddWorkVo>(TotalAddWorkVo.class);
        util.exportExcel(response, list, "加项统计");
    }


}

