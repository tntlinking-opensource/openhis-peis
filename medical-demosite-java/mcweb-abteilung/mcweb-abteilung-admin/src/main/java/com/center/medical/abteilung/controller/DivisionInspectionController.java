package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.vo.AdiconGridDataVo;
import com.center.medical.abteilung.bean.vo.DivisionInspectionVo;
import com.center.medical.abteilung.service.SectionResultMainService;
import com.center.medical.bean.model.Branch;
import com.center.medical.bean.model.Peispatientexamitem;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.reception.bean.param.DIGriddataParam;
import com.center.medical.reception.bean.param.DivisionInspectionParam;
import com.center.medical.reception.bean.param.SetAdiconParam;
import com.center.medical.reception.service.PeispatientexamitemService;
import com.center.medical.system.service.BranchService;
import com.center.medical.system.service.ISysBranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 科室列表-检验科(SectionResultMain)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:53
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "科室列表-检验科")
@RequestMapping("abteilung/divisionInspection")
public class DivisionInspectionController extends BaseController {
    /**
     * 服务对象
     */
    private final SectionResultMainService sectionResultMainService;
    private final MapperFacade mapperFacade;
    private final BranchService branchService;
    private final PeispatientexamitemService peispatientexamitemService;
    private final ISysBranchService isysBranchService;


    /**
     * 读卡
     *
     * @return 所有数据
     */
    @GetMapping("/search")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "读卡", notes = "读卡")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientCode", value = "体检号"),
            @ApiImplicitParam(name = "autoFill", value = "是否补0 ，true是false否"),
            @ApiImplicitParam(name = "KsId", value = "科室id")
    })
    public R<DivisionInspectionVo> getPage(String patientCode, String KsId, String autoFill) {
        //体检号补0
        if ("true".equals(autoFill)) {
            patientCode = ToolUtil.patientCode(patientCode, isysBranchService.getBranchFlag(null));
        } else {
            patientCode = patientCode.trim().toUpperCase();
        }
        //搜索
        DivisionInspectionVo divisionInspectionVo = sectionResultMainService.search(patientCode, KsId);
        return R.ok(divisionInspectionVo);
    }


    /**
     * 检验科结果分页查询
     *
     * @param divisionInspectionParam
     * @return
     */
    @GetMapping("/searchDivision")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "检验科结果分页查询", notes = "检验科结果分页查询")
    public R<IPage<Peispatientexamitem>> searchDivision(PageParamSimple pageParamSimple, DivisionInspectionParam divisionInspectionParam) {
        PageParam<Peispatientexamitem> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<Peispatientexamitem> iPage = peispatientexamitemService.searchDivision(page, divisionInspectionParam);
        return R.ok(iPage);
    }


    /**
     * getgriddata
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getgriddata")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取收费项目表格数据", notes = "获取收费项目表格数据")
    public R<IPage<Peispatientexamitem>> getgriddata(PageParamSimple pageParamSimple, DIGriddataParam param) {
        PageParam<Peispatientexamitem> page = mapperFacade.map(pageParamSimple, PageParam.class);
        if (StringUtils.isNotEmpty(param.getAutoFill()) &&"true".equals(param.getAutoFill())){
            String patientCode = ToolUtil.patientCode(param.getPatientCode(), isysBranchService.getBranchFlag(null));
            param.setPatientCode(patientCode);
        }
        IPage<Peispatientexamitem> iPage = peispatientexamitemService.getgriddata(page, param);
        return R.ok(iPage);
    }


    /**
     * 反审核
     *
     * @param divisionInspectionParam
     * @return
     */
    @PutMapping("/reverse")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "反审核", notes = "反审核")
    public R reverse(DivisionInspectionParam divisionInspectionParam) {
        Boolean b = sectionResultMainService.reverse(divisionInspectionParam);
        return R.toResult(b);
    }


    /**
     * 审核
     *
     * @param divisionInspectionParam
     * @return
     */
    @PostMapping("/saOrUp")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "审核", notes = "审核")
    public R saOrUp(@RequestBody DivisionInspectionParam divisionInspectionParam) {
        Boolean b = sectionResultMainService.saOrUp(divisionInspectionParam);
        return R.toResult(b);
    }


    /**
     * 保存结伦词
     *
     * @param divisionInspectionParam
     * @return
     */
    @PostMapping("/saveJlc")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "保存结伦词", notes = "保存结伦词")
    public R saveJlc(@RequestBody DivisionInspectionParam divisionInspectionParam) {
        Boolean b = sectionResultMainService.saveJlc(divisionInspectionParam);
        return R.toResult(b);
    }


    /**
     * 清除数据
     *
     * @param patientCode
     * @return
     */
    @DeleteMapping("/clear")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "清除数据", notes = "清除数据")
    public R clear(String patientCode) {
        return R.toResult(this.sectionResultMainService.clear(patientCode));
    }


    /**
     * 设置艾迪康代码,获取检验科体检者收费项目列表数据
     *
     * @param patientCode
     * @return
     */
    @GetMapping("/getAdiconGridData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "设置艾迪康代码-获取数据", notes = "设置艾迪康代码,获取检验科体检者收费项目列表数据")
    public R getAdiconGridData(String patientCode) {
        List<AdiconGridDataVo> list = sectionResultMainService.getAdiconGridData(patientCode);
        return R.ok(list);
    }


    /**
     * 设置艾迪康代码
     *
     * @param testGrid
     * @return
     */
    @PostMapping("/setAdicon")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "设置艾迪康代码-保存", notes = "设置艾迪康代码-保存")
    public R setAdicon(@RequestBody List<SetAdiconParam> testGrid) {
        Boolean b = sectionResultMainService.setAdicon(testGrid);
        return R.toResult(b);
    }


    /**
     * 体检号补0
     *
     * @param code
     * @return
     */
    private String patientCode(String code) {
        if (StringUtils.isBlank(code)) {
            return code;
        }
        int length = code.trim().length();
        // 体检号长度为0或者体检号长度确定为12位，直接返回
//        if(length==0 || length == 12){
        if (length == 0 || length >= 12) {
            return code;
        }
        List<Branch> branch = branchService.list(new QueryWrapper<Branch>().eq("IS_DEFAULT", 1));
        StringBuilder builder = new StringBuilder((branch == null || branch.size() == 0) ? "" : ((Branch) (branch.get(0))).getJm());
        // 自动补0
        code = String.format("%10s", code).replace(" ", "0");
        builder.append(code);

        return builder.toString();
    }
}

