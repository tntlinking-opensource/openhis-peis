package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.param.DFsaOrUpParam;
import com.center.medical.abteilung.bean.param.DivisionFaircheckParam;
import com.center.medical.abteilung.bean.param.GetSignParam;
import com.center.medical.abteilung.bean.vo.DivisionFaircheckVo;
import com.center.medical.abteilung.bean.vo.GetSignVo;
import com.center.medical.abteilung.service.DivisionFaircheckService;
import com.center.medical.abteilung.service.SectionResultMainService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.Basconclusion;
import com.center.medical.data.bean.model.Basexamltem;
import com.center.medical.data.bean.model.BasexamltemSign;
import com.center.medical.data.service.BasconclusionService;
import com.center.medical.data.service.BasexamltemService;
import com.center.medical.data.service.BasexamltemSignService;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.service.BranchService;
import com.center.medical.system.service.ISysBranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 科室列表-一般检查(SectionResultMain)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:53
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "科室列表-一般检查")
@RequestMapping("abteilung/divisionFaircheck")
public class DivisionFaircheckController extends BaseController {
    /**
     * 服务对象
     */
    private final SectionResultMainService sectionResultMainService;
    private final MapperFacade mapperFacade;
    private final BranchService branchService;
    private final PeispatientService peispatientService;
    private final DivisionFaircheckService divisionFaircheckService;
    private final BasexamltemSignService basexamltemSignService;
    private final BasexamltemService basexamltemService;
    private final BasconclusionService basconclusionService;
    private final ISysBranchService iSysBranchService;



    /**
     * 读卡
     *
     * @return 所有数据
     */
    @GetMapping("/search")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "读卡", notes = "读卡")
    public R<DivisionFaircheckVo> search(DivisionFaircheckParam divisionFaircheckParam) {
        //体检号补0
        String code = divisionFaircheckParam.getPatientCode();
        if ("true".equals(divisionFaircheckParam.getAutoFill())) {
            divisionFaircheckParam.setPatientCode(ToolUtil.patientCode(code, iSysBranchService.getBranchFlag(null)));
        } else {
            divisionFaircheckParam.setPatientCode(code.trim().toUpperCase());
        }
        //搜索
        DivisionFaircheckVo divisionInspectionVo = sectionResultMainService.searchFaircheck(divisionFaircheckParam);
        return R.ok(divisionInspectionVo);
    }


    /**
     * 体检者表格数据
     *
     * @param pageParamSimple
     * @param ksId
     * @return
     */
    @GetMapping("/listData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "体检者表格数据", notes = "体检者表格数据")
    public R<IPage<Peispatient>> listData(PageParamSimple pageParamSimple, String ksId) {
        PageParam<Peispatient> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<Peispatient> iPage = peispatientService.listData(page, ksId);
        return R.ok(iPage);
    }


    /**
     * 审核
     *
     * @param param
     * @return
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "审核", notes = "审核")
    public R saveOrUpdate(@RequestBody DFsaOrUpParam param) {
        Boolean b = divisionFaircheckService.saOrUp(param);
        return R.toResult(b);
    }


    /**
     * 获取体征词
     *
     * @param getSignParam
     * @return
     */
    @GetMapping("/getSign")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取体征词", notes = "获取体征词")
    public R<GetSignVo> getSign(GetSignParam getSignParam) {

        GetSignVo getSignVo = new GetSignVo();

        if (ObjectUtils.isNotEmpty(getSignParam.getId())) {
            BasexamltemSign sign = basexamltemSignService.getInfoById(getSignParam.getId());
            Basexamltem item = basexamltemService.getInfoById(sign.getInspectId());
            Basconclusion con = basconclusionService.getById(sign.getResultId());
            //设置属性
            getSignVo.setJcxm(item.getExamitemName());
            getSignVo.setTzc(sign.getName());
            getSignVo.setTzcId(getSignParam.getId());
            getSignVo.setJlcId(sign.getResultId());
            if (ObjectUtils.isNotEmpty(con)) {
                getSignVo.setJlcName(con.getName());
            }
            getSignVo.setIntensiveLevel(sign.getIntensiveLevel());
        } else {
            getSignVo = divisionFaircheckService.getSign(getSignParam);
        }
        getSignVo.setExamType(getSignParam.getExamItemType());
        return R.ok(getSignVo);
    }


    /**
     * 结伦词列表数据
     *
     * @param patientCode
     * @param ksId
     * @return
     */
    @GetMapping("/jlcData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "结伦词列表数据", notes = "结伦词列表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientCode", value = "体检号"),
            @ApiImplicitParam(name = "ksId", value = "科室id"),
            @ApiImplicitParam(name = "autoFill", value = "是否补全，true是false否")
    })
    public R<List<GetSignVo>> jlcData(String patientCode, String ksId,String autoFill) {
        //补全体检号
        if (StringUtils.isNotEmpty(autoFill) && "true".equals(autoFill)) {
            patientCode = ToolUtil.patientCode(patientCode, iSysBranchService.getBranchFlag(null));
        }
        List<GetSignVo> list = divisionFaircheckService.jlcData(patientCode, ksId);
        return R.ok(list);
    }


    /**
     * 保存结伦词
     *
     * @param param
     * @return
     */
    @PostMapping("/saveJlc")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "保存结伦词", notes = "保存结伦词")
    public R saveJlc(@RequestBody DivisionFaircheckParam param) {
        Boolean b = divisionFaircheckService.saveJlc(param);
        return R.toResult(b);
    }


    /**
     * 反审核
     *
     * @param patientCode
     * @param ksId
     * @return
     */
    @PutMapping("/reverse")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "反审核", notes = "反审核")
    public R reverse(String patientCode, String ksId) {
        Boolean b = divisionFaircheckService.reverse(patientCode, ksId);
        return R.toResult(b);
    }


    /**
     * 营养状况下拉数据
     *
     * @param patientCode
     * @param ksId
     * @return
     */
    @GetMapping("/getCommonStateData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "营养状况下拉数据", notes = "营养状况下拉数据")
    public R<List<BasexamltemSign>> getCommonStateData(String patientCode, String ksId) {
        List<BasexamltemSign> list = basexamltemService.getCommonStateData();
        return R.ok(list);
    }





}

