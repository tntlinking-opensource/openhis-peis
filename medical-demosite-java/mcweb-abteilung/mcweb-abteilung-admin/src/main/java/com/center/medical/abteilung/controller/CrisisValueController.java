package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.param.CrisisValueParam;
import com.center.medical.abteilung.bean.param.CrisisValueSaveParam;
import com.center.medical.abteilung.bean.param.CrisisValuesaOrUpParam;
import com.center.medical.abteilung.bean.vo.CrisisValueVo;
import com.center.medical.abteilung.bean.vo.GetKsVo;
import com.center.medical.abteilung.bean.vo.RiskclientInfoVo;
import com.center.medical.abteilung.service.CrisisValueService;
import com.center.medical.bean.model.OtherReport;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.sellcrm.bean.model.Riskclient;
import com.center.medical.sellcrm.bean.model.Riskclientcon;
import com.center.medical.sellcrm.bean.vo.AllOrgVo;
import com.center.medical.sellcrm.service.RiskclientconService;
import com.center.medical.service.OtherReportService;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.service.ISysDeptService;
import com.center.medical.system.service.ISysUserService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
 * 危机值提报(Riskclient)表控制层
 *
 * @author ay
 * @since 2023-01-29 14:00:44
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "危机值提报")
@RequestMapping("/abteilung/CrisisValue")
public class CrisisValueController extends BaseController {
    /**
     * 服务对象
     */
    private final CrisisValueService crisisValueService;
    private final MapperFacade mapperFacade;
    private final RiskclientconService riskclientconService;
    private final ISysDeptService sysDeptService;
    private final ISysUserService sysUserService;
    private final PeispatientService peispatientService;
    private final OtherReportService otherReportService;


    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【危机值提报】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("获取科室下拉", "GET", "/basconclusion/findAllDepartment", "09.科室系统->科室管理-危急值提报->获取科室下拉", null),
                new InterfaceVo("获取用户下拉", "GET", "/report/healthAssociate/getAllUser", "09.科室系统->科室管理-危急值提报->获取用户下拉", null),
                new InterfaceVo("获取通知方式", "GET", "/SignInInspection/getIssueWayData", "09.科室系统->科室管理-危急值提报->获取通知方式", null)
        );
        return R.ok(new FunctionVo("09.科室系统", "危机值提报", interfaceVos, "09.科室系统"));
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
    @ApiOperation(value = "分页查询", notes = "分页查询高危人员管理表")
    public R<IPage<CrisisValueVo>> getPage(PageParamSimple pageParamSimple, CrisisValueParam param) {
        PageParam<CrisisValueVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.crisisValueService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查高危人员管理表详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<RiskclientInfoVo> selectOne(@PathVariable String id) {
        Riskclient riskclient = crisisValueService.getInfoById(id);
        RiskclientInfoVo vo = new RiskclientInfoVo();
        vo.setRiskclient(riskclient);
        if (riskclient != null) {
            //高危人员关联表
            Riskclientcon riskclientcon = riskclientconService.getOne(new QueryWrapper<Riskclientcon>().eq("riskid", id));
            // 由体检号得到体检者表里面的个人信息
            Peispatient peispatient = peispatientService.getByPatientCode(riskclient.getTjid());
            //返回字段
            if (ObjectUtils.isNotEmpty(peispatient)) {
                vo.setDoctorapply(peispatient.getDoctorapply());
                vo.setOrgName(peispatient.getOrgName());
                vo.setOrgDepart(peispatient.getOrgDepart());
                //是否生成临时报告
                OtherReport otherReport = otherReportService.getOne(new QueryWrapper<OtherReport>()
                        .eq("patientcode",peispatient.getPatientcode()).eq("report_type",3)
                );
                vo.setInterimReport(ObjectUtils.isNotEmpty(otherReport)?1:0);
            }
            if (ObjectUtils.isNotEmpty(riskclientcon)) {
                //危急值级别及小结
                vo.setWjzjb(riskclientcon.getWjzjb());
                vo.setWjzxj(riskclientcon.getWjzxj());
            }
            //科室名称
            SysDept sysDept = sysDeptService.getByDeptNo(riskclient.getReportDivision());
            if (ObjectUtils.isNotEmpty(sysDept)){
                vo.setDeptName(sysDept.getDeptName());
            }
        }
        return R.ok(vo);
    }

    /**
     * 新增数据
     *
     * @param param
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加高危人员管理表")
    @Log(title = "高危人员管理表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"riskclient.id"})
    public R saveOrUpdate(@RequestBody CrisisValuesaOrUpParam param) {
        return R.toResult(this.crisisValueService.saOrUp(param));
    }


    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除高危人员管理表")
    @Log(title = "高危人员管理表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.crisisValueService.removeCrisisValue(ids));
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "危急值提报", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('::export')")
    @ApiOperation(value = "导出", notes = "导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrisisValueParam param) {
        List<CrisisValueVo> list = crisisValueService.getExportData(param);
        for (int i = 0; i < list.size(); i++) {
            CrisisValueVo vo = list.get(i);
            vo.setRownum(i+1);
        }
        ExcelUtil<CrisisValueVo> util = new ExcelUtil<CrisisValueVo>(CrisisValueVo.class);
        util.exportExcel(response, list, "危急值提报");
    }


    /**
     * 获取公司下拉
     *
     * @param pageParamSimple
     * @param key
     * @return
     */
    @GetMapping("/getListData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取公司下拉", notes = "获取公司下拉,本分中心的")
    @ApiImplicitParam(name = "key", value = "输入码")
    public R<IPage<AllOrgVo>> getListData(PageParamSimple pageParamSimple, String key) {
        PageParam<AllOrgVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<AllOrgVo> list = crisisValueService.getListData(page, key);
        return R.ok(list);
    }


    /**
     * 业务处理
     *
     * @param param
     * @return
     */
    @PostMapping("/saveYw")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "业务处理", notes = "业务处理")
    public R saveYw(@RequestBody CrisisValueSaveParam param) {
        return R.toResult(this.crisisValueService.saveYw(param));
    }

    /**
     * 回访处理
     *
     * @param param
     * @return
     */
    @PostMapping("/saveHf")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "回访处理", notes = "回访处理")
    public R saveHf(@RequestBody CrisisValueSaveParam param) {
        return R.toResult(this.crisisValueService.saveHf(param));
    }


    /**
     * 专家处理
     *
     * @param param
     * @return
     */
    @PostMapping("/saveZj")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "专家处理", notes = "专家处理")
    public R saveZj(@RequestBody CrisisValueSaveParam param) {
        return R.toResult(this.crisisValueService.saveZj(param));
    }


    /**
     * 获取科室及体检结果
     * @param patientcode
     * @param key
     * @return
     */
    @GetMapping("/getKs")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取科室及体检结果", notes = "获取科室及体检结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "key", value = "科室输入码")
    })
    public R<List<GetKsVo>> getKs(String patientcode, String key) {
        List<GetKsVo> list = crisisValueService.getKs(patientcode,key);
        return R.ok(list);
    }
}

