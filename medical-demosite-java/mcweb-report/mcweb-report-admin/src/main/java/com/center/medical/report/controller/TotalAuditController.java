package com.center.medical.report.controller;

import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.bean.model.Report;
import com.center.medical.report.bean.param.TotalAuditParam;
import com.center.medical.report.bean.vo.TotalAuditVo;
import com.center.medical.report.service.ReportService;
import com.center.medical.system.bean.vo.GetNameDataVo;
import com.center.medical.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 报告审核统计(Report)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-23 17:11:37
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "报告审核统计")
@RequestMapping("report/totalAudit")
public class TotalAuditController extends BaseController {
    /**
     * 服务对象
     */
    private final ReportService reportService;
    private final MapperFacade mapperFacade;
    private final ISysUserService sysUserService;


    /**
     * 【报告审核统计】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【报告审核统计】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("分页查询", "GET", "/report/totalAudit/page", "10.总检-报告系统->报告管理-报告审核统计->分页查询", null),
                new InterfaceVo("获取审核人姓名集合", "GET", "/report/totalAudit/getNameData", "10.总检-报告系统->报告管理-报告审核统计->获取审核人姓名集合", null),
                new InterfaceVo("获取审核人姓名和部门下拉", "GET", "/report/totalAudit/getDepData", "10.总检-报告系统->报告管理-报告审核统计->获取审核人姓名和部门下拉", null)
        );
        return R.ok(new FunctionVo("10.总检-报告系统", "报告柜子管理", interfaceVos, "10.总检/报告系统"));
    }

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<List<TotalAuditVo>> getPage(PageParamSimple pageParamSimple, TotalAuditParam param) {
        PageParam<Report> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reportService.getTotalAuditPage(page, param));
    }


    /**
     * 获取审核人姓名集合
     * @return
     */
    @GetMapping("/getNameData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取审核人姓名集合", notes = "获取审核人姓名集合")
    public R getNameData() {
        return R.ok(this.sysUserService.getNameData());
    }


    /**
     * 获取审核人姓名和部门下拉
     * @param pageParamSimple
     * @param key
     * @return
     */
    @GetMapping("/getDepData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取审核人姓名和部门下拉", notes = "获取审核人姓名和部门下拉")
    @ApiImplicitParam(name = "key", value = "模糊查询的user_name")
    public R getDepData(PageParamSimple pageParamSimple,String key) {
        PageParam<GetNameDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sysUserService.getDepData(page,key));
    }


}

