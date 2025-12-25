package com.center.medical.report.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.param.ReportSearchCodeParam;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.bean.vo.SearchCodeVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.data.service.NotificationmethodService;
import com.center.medical.report.bean.dto.GetReportDto;
import com.center.medical.report.bean.param.PhoneInformParam;
import com.center.medical.report.bean.param.ReceiveFromDataParam;
import com.center.medical.report.bean.vo.PhoneInformVo;
import com.center.medical.report.service.ReportService;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.bean.vo.AllUserDataVo;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 报告领取-健康报告领取(Report)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-23 17:11:37
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "报告领取-健康报告领取")
@RequestMapping("report/healthGetReport")
public class HealthGetReportController extends BaseController {
    /**
     * 服务对象
     */
    private final ReportService reportService;
    private final MapperFacade mapperFacade;
    private final ISysBranchService iSysBranchService;
    private final PeispatientService peispatientService;
    private final NotificationmethodService notificationmethodService;
    private final ISysUserService sysUserService;

    /**
     * 【报告领取-健康报告领取】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【报告领取-健康报告领取】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("分页查询", "GET", "/report/healthGetReport/page", "10.总检-报告系统->报告管理-报告领取-健康报告领取->分页查询", null),
                new InterfaceVo("领取", "PUT", "/report/healthGetReport/receive", "10.总检-报告系统->报告管理-报告领取-健康报告领取->领取", null),
                new InterfaceVo("反领取", "PUT", "/report/healthGetReport/unNotice", "10.总检-报告系统->报告管理-报告领取-健康报告领取->反领取", null),
                new InterfaceVo("导出数据", "POST", "/report/healthGetReport/unNotice", "10.总检-报告系统->报告管理-报告领取-健康报告领取->导出数据", null),
                new InterfaceVo("搜索输入码", "GET", "/report/healthGetReport/searchCode", "10.总检-报告系统->报告管理-报告领取-健康报告领取->搜索输入码", null));
        return R.ok(new FunctionVo("10.总检-报告系统", "报告领取", interfaceVos, "10.总检/报告系统"));
    }


    /**
     * 所有用户下拉
     *
     * @param key
     * @return
     */
    @GetMapping("/getAllUserData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "所有用户下拉", notes = "所有用户下拉")
    @ApiImplicitParam(name = "key", value = "搜索的用户名或输入码")
    public R getAllUserData(String key) {
        List<AllUserDataVo> list = sysUserService.getAllUserData(key);
        return R.ok(list);
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
    public R<IPage<PhoneInformVo>> getPage(PageParamSimple pageParamSimple, PhoneInformParam param) {
        PageParam<PhoneInformVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<PhoneInformVo> iPage = reportService.getReceiveReportData(page, param, 1, Constants.HEALTHPHONE);
        return R.ok(iPage);
    }


    /**
     * 领取
     *
     * @param param
     * @return
     */
    @PutMapping("/receive")
    @Log(title = "健康报告领取", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "领取", notes = "领取")
    public R receive(ReceiveFromDataParam param) {
        Boolean b = reportService.receive(param, Constants.HEALTHPHONE);
        return R.toResult(b);
    }


    /**
     * 反领取
     *
     * @param ids
     * @return
     */
    @PutMapping("/unNotice")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "反领取", notes = "反领取")
    @ApiImplicitParam(name = "ids", value = "id集合，list集合，逗号分割")
    public R unNotice(@RequestParam("ids") List<String> ids) {
        Boolean b = reportService.unNotice(ids, Constants.HEALTHPHONE);
        return R.toResult(b);
    }


    /**
     * 导出数据
     *
     * @param response
     * @param param
     */
    @PostMapping("/export")
    @ApiOperation(value = "导出数据", notes = "导出Excel数据")
    public void export(HttpServletResponse response, PhoneInformParam param) {
        List<GetReportDto> list = reportService.exportGetReport(param, 0);
        ExcelUtil<GetReportDto> util = new ExcelUtil<GetReportDto>(GetReportDto.class);
        util.exportExcel(response, list, "健康报告领取");
    }


    /**
     * 搜索输入码
     *
     * @param param
     * @return
     */
    @GetMapping("/searchCode")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "搜索输入码", notes = "搜索输入码")
    @ApiImplicitParam(name = "patientCode", value = "体检号")
    public R<List<SearchCodeVo>> searchCode(ReportSearchCodeParam param) {
        //补全体检号
        if (ObjectUtils.isNotEmpty(param.getPatientCode())) {
            String patientCode = ToolUtil.patientCode(param.getPatientCode(), iSysBranchService.getBranchFlag(null));
            param.setPatientCode(patientCode);
        }
        param.setTjlx(0);
        //搜索体检号
        List<SearchCodeVo> peispatientList = peispatientService.searchCode(param);
        if (ObjectUtils.isEmpty(peispatientList)) {
            throw new ServiceException("搜索数据不存在！");
        }
        return R.ok(peispatientList);
    }
}

