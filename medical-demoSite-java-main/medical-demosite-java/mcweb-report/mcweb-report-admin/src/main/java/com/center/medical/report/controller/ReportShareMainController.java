package com.center.medical.report.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.ReportShareTwo;
import com.center.medical.bean.param.AddReportShareParam;
import com.center.medical.bean.param.ReportSharePageParam;
import com.center.medical.bean.param.UpReportShareParam;
import com.center.medical.bean.vo.ReportShareMainVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.report.service.ReportShareMainService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

/**
 * 报告分享主表(ReportShareMain)接口
 *
 * @author ay
 * @since 2023-09-19 16:19:51
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "分享报告及分享报告统计")
@RequestMapping("report/reportShareMain")
public class ReportShareMainController extends BaseController {
    /**
     * 服务对象
     */
    private final ReportShareMainService reportShareMainService;
    private final MapperFacade mapperFacade;


    /**
     * 新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/reportShare")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "分享报告", notes = "分享报告")
    @Log(title = "分享报告", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"reportShareMain.id"})
    public R<ReportShareMainVo> insert(@RequestBody AddReportShareParam param) {
        ReportShareMainVo reportShareMainVo = reportShareMainService.reportShare(param);
        return R.ok(reportShareMainVo);
    }


    /**
     * 修改数据
     *
     * @param param 实体对象
     * @return 修改结果
     */
    @PutMapping("/updateReportShare")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新分享报告", notes = "更新分享报告")
    @Log(title = "更新分享报告", businessType = BusinessType.UPDATE)
    public R update(@RequestBody UpReportShareParam param) {
        return R.ok(this.reportShareMainService.updateReportShare(param));
    }



    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分享报告统计-分页查询", notes = "分享报告统计分页查询")
    public R<IPage<ReportShareMainVo>> getPage(PageParamSimple pageParamSimple, ReportSharePageParam param) {
        PageParam<ReportShareMainVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reportShareMainService.getPage(page, param));
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/details")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "分享报告统计-详情", notes = "分享报告统计详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<IPage<ReportShareTwo>> details(PageParamSimple pageParamSimple,String id) {
        PageParam<ReportShareTwo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<ReportShareTwo> iPage = reportShareMainService.details(page,id);
        return R.ok(iPage);
    }





    /**
     * 分享报告过期
     *
     * @return 修改结果
     */
    @PutMapping("/reportShareMainExpire")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "分享报告过期", notes = "分享报告过期")
    @Log(title = "更新分享报告", businessType = BusinessType.UPDATE)
    public R reportShareMainExpire() {
        return R.toResult(this.reportShareMainService.reportShareMainExpire());
    }





}
