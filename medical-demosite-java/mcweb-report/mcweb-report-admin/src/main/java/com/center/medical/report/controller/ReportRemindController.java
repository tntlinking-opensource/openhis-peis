package com.center.medical.report.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.bean.model.Report;
import com.center.medical.report.bean.param.ReportRemindParam;
import com.center.medical.report.bean.vo.ReportRemindVo;
import com.center.medical.report.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 体检报告待领提醒(Report)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-23 17:11:37
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "体检报告待领提醒")
@RequestMapping("report/remind")
public class ReportRemindController extends BaseController {
    /**
     * 服务对象
     */
    private final ReportService reportService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询体检报告待领提醒
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询体检报告待领提醒")
    public R<IPage<ReportRemindVo>> getPage(PageParamSimple pageParamSimple, ReportRemindParam param) {
        PageParam<Report> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reportService.getReportRemindPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查BG报告主表详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<Report> selectOne(@PathVariable String id) {
        return R.ok(this.reportService.getInfoById(id));
    }


}

