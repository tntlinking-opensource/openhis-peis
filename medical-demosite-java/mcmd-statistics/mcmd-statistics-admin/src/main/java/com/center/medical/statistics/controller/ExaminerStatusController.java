package com.center.medical.statistics.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.statistics.bean.param.ExaminerStatusParam;
import com.center.medical.statistics.bean.vo.ExaminerStatusVo;
import com.center.medical.statistics.service.ExaminerStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * QT体检者表(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-14 16:40:23
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "体检状态统计")
@RequestMapping("statistics/examinerStatus")
public class ExaminerStatusController extends BaseController {
    /**
     * 服务对象
     */
    private final ExaminerStatusService examinerStatusService;
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
    @ApiOperation(value = "分页查询", notes = "分页查询QT体检者表")
    public R<IPage<ExaminerStatusVo>> getPage(PageParamSimple pageParamSimple, ExaminerStatusParam param) {
        PageParam<ExaminerStatusVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.examinerStatusService.getList(page, param));
    }



    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "体检者状态统计", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "导出体检者状态统计")
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExaminerStatusParam param) {
        List<ExaminerStatusVo> list = examinerStatusService.exportData(param);
        ExcelUtil<ExaminerStatusVo> util = new ExcelUtil<ExaminerStatusVo>(ExaminerStatusVo.class);
        util.exportExcel(response, list, "体检者状态统计");
    }


}

