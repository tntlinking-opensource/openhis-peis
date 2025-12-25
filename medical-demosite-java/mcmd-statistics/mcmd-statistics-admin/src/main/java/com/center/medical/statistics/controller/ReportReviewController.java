package com.center.medical.statistics.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.data.bean.model.Patienttype;
import com.center.medical.data.service.PatienttypeService;
import com.center.medical.statistics.bean.param.ReportReviewParam;
import com.center.medical.statistics.bean.vo.ReportReviewVo;
import com.center.medical.statistics.service.ReportReviewService;
import com.center.medical.system.bean.vo.AllUserDataVo;
import com.center.medical.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 报告审核工作量(ReportContent)接口
 *
 * @author ay
 * @since 2023-08-14 14:54:49
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "报告审核工作量")
@RequestMapping("statistics/ReportReview")
public class ReportReviewController extends BaseController {
    /**
     * 服务对象
     */
    private final ReportReviewService reportReviewService;
    private final MapperFacade mapperFacade;
    private final PatienttypeService patienttypeService;
    private final ISysUserService sysUserService;


    /**
     * 获取会员类型
     *
     * @return
     */
    @GetMapping("/getPatientTypeData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取会员类型", notes = "获取体检者会员类型", position = 1)
    public R<List<Patienttype>> getPatientTypeData() {
        List<Patienttype> pattList = patienttypeService.list();
        return R.ok(pattList);
    }

    /**
     * 所有用户下拉
     *
     * @param key
     * @return
     */
    @GetMapping("/getAllUserData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "所有用户下拉", notes = "所有用户下拉", position = 2)
    @ApiImplicitParam(name = "key", value = "搜索的用户名或输入码")
    public R getAllUserData(String key) {
        List<AllUserDataVo> list = sysUserService.getAllUserData(key);
        return R.ok(list);
    }


    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param   查询条件
     * @return 所有数据
     */
    @GetMapping("/getListData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询-终审", notes = "分页查询生成报告内容", position = 3)
    public R<IPage<ReportReviewVo>> getPage(PageParamSimple pageParamSimple, ReportReviewParam param) {
        PageParam<ReportReviewVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reportReviewService.getPage(page, param));
    }

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param   查询条件
     * @return 所有数据
     */
    @GetMapping("/getTotalData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取合计数据-终审", notes = "获取合计数据", position = 4)
    public R<IPage<ReportReviewVo>> getTotalData(PageParamSimple pageParamSimple, ReportReviewParam param) {
        PageParam<ReportReviewVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reportReviewService.getTotalData(page, param));
    }




    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "报告审核工作量", businessType = BusinessType.EXPORT)
    @ApiOperation(value = "导出-终审", notes = "导出报告审核工作量", position = 5)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ReportReviewParam param) {
        List<ReportReviewVo> list = reportReviewService.exportData(param);
        ExcelUtil<ReportReviewVo> util = new ExcelUtil<ReportReviewVo>(ReportReviewVo.class);
        util.exportExcel(response, list, "终审报告审核工作量","终审报告审核工作量");
    }




    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param   查询条件
     * @return 所有数据
     */
    @GetMapping("/getFistListData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询-一审", notes = "分页查询-一审", position = 6)
    public R<IPage<ReportReviewVo>> getFistListData(PageParamSimple pageParamSimple, ReportReviewParam param) {
        PageParam<ReportReviewVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reportReviewService.getFistListData(page, param));
    }


    /**
     * 获取合计数据-一审
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getFistTotalData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取合计数据-一审", notes = "获取合计数据", position = 7)
    public R<IPage<ReportReviewVo>> getFistTotalData(PageParamSimple pageParamSimple, ReportReviewParam param) {
        PageParam<ReportReviewVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reportReviewService.getFistTotalData(page, param));
    }



    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "报告审核工作量", businessType = BusinessType.EXPORT)
    @ApiOperation(value = "导出-一审", notes = "导出报告审核工作量", position = 8)
    @PostMapping("/exportFist")
    public void exportFist(HttpServletResponse response, ReportReviewParam param) {
        List<ReportReviewVo> list = reportReviewService.exportFistData(param);
        ExcelUtil<ReportReviewVo> util = new ExcelUtil<ReportReviewVo>(ReportReviewVo.class);
        util.exportExcel(response, list, "初审报告审核工作量","初审报告审核工作量");
    }




    /**
     * 分页查询-二审
     *
     * @param pageParamSimple 分页参数
     * @param param   查询条件
     * @return 所有数据
     */
    @GetMapping("/getSecondListData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询-二审", notes = "分页查询-二审", position = 9)
    public R<IPage<ReportReviewVo>> getSecondListData(PageParamSimple pageParamSimple, ReportReviewParam param) {
        PageParam<ReportReviewVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reportReviewService.getSecondListData(page, param));
    }


    /**
     * 获取合计数据-一审
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getSecondTotalData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取合计数据-二审", notes = "获取合计数据", position = 10)
    public R<IPage<ReportReviewVo>> getSecondTotalData(PageParamSimple pageParamSimple, ReportReviewParam param) {
        PageParam<ReportReviewVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reportReviewService.getSecondTotalData(page, param));
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "报告审核工作量", businessType = BusinessType.EXPORT)
    @ApiOperation(value = "导出-二审", notes = "导出报告审核工作量", position = 11)
    @PostMapping("/exportSecond")
    public void exportSecond(HttpServletResponse response, ReportReviewParam param) {
        List<ReportReviewVo> list = reportReviewService.exportSecond(param);
        ExcelUtil<ReportReviewVo> util = new ExcelUtil<ReportReviewVo>(ReportReviewVo.class);
        util.exportExcel(response, list, "二审报告审核工作量","二审报告审核工作量");
    }
}

