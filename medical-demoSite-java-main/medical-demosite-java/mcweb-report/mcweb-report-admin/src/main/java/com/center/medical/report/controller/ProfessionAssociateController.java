package com.center.medical.report.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Chest;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Report;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.data.bean.model.Notificationmethod;
import com.center.medical.data.service.NotificationmethodService;
import com.center.medical.report.bean.dto.HealthAssociateExportDto;
import com.center.medical.report.bean.model.ExportStatistics;
import com.center.medical.report.bean.model.Formdata;
import com.center.medical.report.bean.param.FormdataAndGriddataParam;
import com.center.medical.report.bean.param.ProfessionAssociateParam;
import com.center.medical.report.bean.param.ReportPatientParam;
import com.center.medical.report.service.ReportService;
import com.center.medical.service.ChestService;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.service.ISysBranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 报告交接-职业报告交接(Report)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-23 17:11:37
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "报告交接-职业报告交接")
@RequestMapping("report/professionAssociate")
public class ProfessionAssociateController extends BaseController {
    /**
     * 服务对象
     */
    private final ReportService reportService;
    private final MapperFacade mapperFacade;
    private final ISysBranchService iSysBranchService;
    private final PeispatientService peispatientService;
    private final NotificationmethodService notificationmethodService;
    private final ChestService chestService;


    /**
     * 【报告交接-职业报告交接】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【报告交接-职业报告交接】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("分页查询", "GET", "/report/professionAssociate/page", "10.总检-报告系统->报告管理-报告交接-健康报告交接->分页查询", null),
                new InterfaceVo("详情", "GET", "/report/professionAssociate/{id}", "10.总检-报告系统->报告管理-报告交接-健康报告交接->详情", null),
                new InterfaceVo("职业报告交接获取体检者数据", "GET", "/report/professionAssociate/getPatientData", "10.总检-报告系统->报告管理-报告交接-健康报告交接->职业报告交接获取体检者数据", null),
                new InterfaceVo("批量通过职业报告交接", "POST", "/report/professionAssociate/saveOrUpdate", "10.总检-报告系统->报告管理-报告交接-健康报告交接->批量通过职业报告交接", null),
                new InterfaceVo("职业报告交接反交接", "POST", "/report/professionAssociate/unaudit", "10.总检-报告系统->报告管理-报告交接-健康报告交接->职业报告交接反交接", null),
                new InterfaceVo("导出数据", "POST", "/report/professionAssociate/export", "10.总检-报告系统->报告管理-报告交接-健康报告交接->导出数据", null),
                new InterfaceVo("报告交接统计", "GET", "/report/professionAssociate/exportStatistics", "10.总检-报告系统->报告管理-报告交接-健康报告交接->报告交接统计", null),
                new InterfaceVo("折线图数据", "GET", "/report/professionAssociate/getChartData", "10.总检-报告系统->报告管理-报告交接-健康报告交接->折线图数据", null)
        );
        return R.ok(new FunctionVo("10.总检-报告系统", "报告交接-职业报告交接", interfaceVos, "10.总检/报告系统"));
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
    @ApiOperation(value = "分页查询", notes = "分页查询职业报告交接")
    public R<IPage<Report>> getPage(PageParamSimple pageParamSimple, ProfessionAssociateParam param) {
        PageParam<Report> page = mapperFacade.map(pageParamSimple, PageParam.class);
        //是否补全体检号
        if ("true".equals(param.getAutoFill())){
            String patientCode = ToolUtil.patientCode(param.getPatientcode(), iSysBranchService.getBranchFlag(null));
            param.setPatientcode(patientCode);
        }
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())){
            DateTime start = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(start);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())){
            DateTime end = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(end);
        }
        //没有决策管理权限
        if (!SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE)){
            param.setXsjlid(SecurityUtils.getUserNo());
        }
        return R.ok(this.reportService.getPaPage(page, param));
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


    /**
     * 职业报告交接获取体检者数据
     * @param param
     * @return
     */
    @GetMapping("/getPatientData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "职业报告交接获取体检者数据", notes = "职业报告交接获取体检者数据")
    public R getPatientData(ReportPatientParam param) {
        List<Peispatient> list = new ArrayList<>();
        List<String> patientcodes = param.getPatientcode();
        if (ObjectUtils.isEmpty(patientcodes)){
            throw new ServiceException("体检号不能为空！");
        }
        for (String patientcode : patientcodes) {
            //是否补全体检号
            if ("true".equals(param.getAutoFill())) {
                patientcode = ToolUtil.patientCode(patientcode, iSysBranchService.getBranchFlag(null));
            }
            Peispatient patient = peispatientService.getOne(new QueryWrapper<Peispatient>().eq("patientcode", patientcode));
            if (ObjectUtils.isNotEmpty(patient)) {
                String grantId = patient.getIdInformway();
                patient.setGrantId(grantId);
                if (StringUtils.isNotBlank(grantId)) {
                    Notificationmethod method = notificationmethodService.getInfoById(grantId);
                    if (ObjectUtils.isNotEmpty(method)) {
                        //发送方式名称
                        patient.setMethodName(method.getMethodName());
                    }
                }
                Report report = reportService.getOne(new QueryWrapper<Report>()
                        .eq("patientcode", patientcode).eq("disease_health", 1));
                if (ObjectUtils.isNotEmpty(report)) {
                    patient.setIsTotal(String.valueOf(report.getIsTotal()));
                }
                if (ObjectUtils.isNotEmpty(report) && ObjectUtils.isNotEmpty(report.getChestNum())) {
                    patient.setChestNum(report.getChestNum());
                } else {
                    //可能会有多个柜子号
                    List<Chest> chest=null;
                    if (ObjectUtils.isNotEmpty(patient.getNumorgresv())) {
                        if ("2".equals(patient.getIdExamtype())) {
                            chest = chestService.list(new QueryWrapper<Chest>()
                                    .eq("tjlx", "2")//如果体检者为综合，则优先综合柜子
                                    .eq("ddh", patient.getNumorgresv().toString())//用订单号而不是单位
                                    .eq("is_delete", 0)//未删除
                                    .orderByAsc("createdate")
                            );
                        }
                        if (ObjectUtils.isEmpty(chest)) {
                            chest = chestService.list(new QueryWrapper<Chest>()
                                    .eq("tjlx", "1")//职业
                                    .eq("ddh", patient.getNumorgresv().toString())//用订单号而不是单位
                                    .eq("is_delete", 0)//未删除
                                    .orderByAsc("createdate")
                            );
                        }
                    }
                    if (CollectionUtil.isNotEmpty(chest)) {
                        patient.setChestNum(chest.get(0).getGzh());
                    } else {
                        // 默认1
                        patient.setChestNum("1");
                    }
                }
                list.add(patient);
            }
        }
        return R.ok(list);
    }


    /**
     * 批量通过职业报告交接
     * @return
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "批量通过职业报告交接", notes = "批量通过职业报告交接")
    public R<IPage<Report>> saveOrUpdate(@RequestBody FormdataAndGriddataParam param) throws Exception{
        Boolean b = reportService.saOrUp(param);
        return R.toResult(b);
    }


    /**
     * 职业报告交接反交接
     * @param param
     * @return
     * @throws Exception
     */
    @PostMapping("/unaudit")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "职业报告交接反交接", notes = "职业报告交接反交接")
    public R unaudit(@RequestBody FormdataAndGriddataParam param){
        Boolean b = reportService.unaudit(param);
        return R.toResult(b);
    }


    /**
     * 批量编辑柜子号保存
     * @param formdata
     * @return
     * @throws Exception
     */
    @PutMapping("/saveEdit")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "批量编辑柜子号保存", notes = "批量编辑柜子号保存")
    public R saveEdit(Formdata formdata) throws Exception{
        Boolean b = reportService.saveEdit(formdata);
        return R.toResult(b);
    }


    /**
     * 导出数据
     * @param response
     * @param param
     */
    @PostMapping("/export")
    @ApiOperation(value = "导出数据", notes = "导出Excel数据")
    public void export(HttpServletResponse response, ProfessionAssociateParam param) {
        //是否补全体检号
        if ("true".equals(param.getAutoFill())){
            String patientCode = ToolUtil.patientCode(param.getPatientcode(), iSysBranchService.getBranchFlag(null));
            param.setPatientcode(patientCode);
        }
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())){
            DateTime start = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(start);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())){
            DateTime end = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(end);
        }
        List<HealthAssociateExportDto> list = reportService.healthAssociateExport(param);
        ExcelUtil<HealthAssociateExportDto> util = new ExcelUtil<HealthAssociateExportDto>(HealthAssociateExportDto.class);
        util.exportExcel(response, list, "职业报告交接");
    }


    /**
     * 报告交接统计
     * @param response
     * @param param
     */
    @PostMapping("/exportStatistics")
    @ApiOperation(value = "报告交接统计", notes = "报告交接统计")
    public void exportStatistics(HttpServletResponse response, ProfessionAssociateParam param) {
        List<ExportStatistics> list = reportService.exportStatistics(param,1);
        ExcelUtil<ExportStatistics> util = new ExcelUtil<ExportStatistics>(ExportStatistics.class);
        util.exportExcel(response, list, "报告交接统计");
    }


    /**
     * 获取折线图数据
     * @return
     */
    @PostMapping("/getChartData")
    @ApiOperation(value = "折线图数据", notes = "折线图数据")
    public R getChartData() {
        Map<String,Object> map = reportService.getChartData(1);
        return R.ok(map);
    }

}

