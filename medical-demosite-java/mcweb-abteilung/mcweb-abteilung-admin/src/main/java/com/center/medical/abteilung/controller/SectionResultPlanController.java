package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.model.SectionResultPlan;
import com.center.medical.abteilung.bean.param.SectionResultPlanParam;
import com.center.medical.abteilung.bean.vo.SectionResultPlanVo;
import com.center.medical.abteilung.service.SectionResultPlanService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.sellcrm.bean.vo.GetOrgsVo;
import com.center.medical.sellcrm.service.SellcustomerService;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.bean.vo.DeptSimpleVo;
import com.center.medical.system.service.BranchService;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysDeptService;
import com.center.medical.system.service.SysDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 科室批量录入结果(SectionResultPlan)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:54
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "科室批量录入")
@RequestMapping("/abteilung/sectionResultPlan")
public class SectionResultPlanController extends BaseController {
    /**
     * 服务对象
     */
    private final SectionResultPlanService sectionResultPlanService;
    private final MapperFacade mapperFacade;
    private final BranchService branchService;
    private final PeispatientService peispatientService;
    private final SysDepartmentService sysDepartmentService;
    private final ISysDeptService sysDeptService;
    private final SellcustomerService sellcustomerService;
    private final ISysBranchService iSysBranchService;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple   分页参数
     * @param sectionResultPlanParam 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询科室批量录入结果")
    public R<IPage<SectionResultPlanVo>> getPage(PageParamSimple pageParamSimple, SectionResultPlanParam sectionResultPlanParam) {
        PageParam<SectionResultPlanVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sectionResultPlanService.getPage(page, sectionResultPlanParam));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查科室批量录入结果详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<SectionResultPlan> selectOne(@PathVariable String id) {
        return R.ok(this.sectionResultPlanService.getInfoById(id));
    }

    /**
     * 保存
     *
     * @param sectionResultPlans 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "保存", notes = "保存")
    public R insert(@RequestBody List<SectionResultPlan> sectionResultPlans) {
        return R.toResult(this.sectionResultPlanService.saOrUp(sectionResultPlans));
    }




    /**
     * 检查体检号
     * @param patientcode
     * @param depId
     * @return
     */
    @GetMapping("/checkCode")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "检查体检号", notes = "检查体检号")
    public R checkCode(String patientcode,String depId) {
        patientcode = ToolUtil.patientCode(patientcode, iSysBranchService.getBranchFlag(null));
        SectionResultPlanVo vo = new SectionResultPlanVo();
        //检查体检号
        Boolean b = sectionResultPlanService.checkCode(patientcode,depId);
        if(b) {
            Peispatient p = peispatientService.getOne(new QueryWrapper<Peispatient>()
                    .eq("patientcode", patientcode));
            vo.setPatientname(p.getPatientname());
            //部门
            SysDept dept = sysDeptService.getByDeptNo(depId);
            if (ObjectUtils.isNotEmpty(dept)){
                vo.setDepName(dept.getDeptName());
                vo.setPatientcode(patientcode);
            }
        }
        return R.ok(vo);
    }

    /**
     * 根据输入码获取科室
     * @param srm
     * @return
     */
    @GetMapping("/getKs")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "根据输入码获取科室", notes = "根据输入码获取科室")
    public R getKs(String srm) {
        List<DeptSimpleVo> list = sysDeptService.getKs(srm);
        return R.ok(list);
    }







    /**
     * 团体名称下拉
     * @param key
     * @return
     */
    @GetMapping("/getOrgs")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "团体名称下拉", notes = "团体名称下拉")
    @ApiImplicitParam(name = "key", value = "团体名称或输入码")
    public R getOrgs(String key) {
        if (ObjectUtils.isNotEmpty(key)){
            key = key.trim();
        }
        List<GetOrgsVo> list = sellcustomerService.getOrgs(key);
        return R.ok(list);
    }

}

