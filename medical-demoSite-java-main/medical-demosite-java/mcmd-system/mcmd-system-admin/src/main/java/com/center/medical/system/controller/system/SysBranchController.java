package com.center.medical.system.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.bean.model.Report;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.bean.param.ChangePaymentMethodParam;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.annotation.RepeatSubmit;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.AjaxResult;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.CodeUtil;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.bean.BeanUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.report.service.ReportService;
import com.center.medical.sellcrm.bean.model.Createmeal;
import com.center.medical.sellcrm.service.CreatemealService;
import com.center.medical.service.PeispatientService;
import com.center.medical.service.PeispatientchargeService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.bean.param.ModifyDepartmentParam;
import com.center.medical.system.bean.param.ModifyPeiStatusParam;
import com.center.medical.system.bean.param.ModifyWorkTypeParam;
import com.center.medical.system.bean.param.UpdateOpenDoctorParam;
import com.center.medical.system.bean.vo.FindProjectStatusVo;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysDeptService;
import com.center.medical.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 分中心管理Controller
 *
 * @author 路飞
 * @date 2022-10-21
 */
@Slf4j
@RestController
@Api(tags = "分中心管理")
@RequiredArgsConstructor
@RequestMapping("/system/branch")
public class SysBranchController extends BaseController {

    private final ISysBranchService sysBranchService;
    private final PeispatientService peispatientService;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final ReportService reportService;
    private final CreatemealService createmealService;

    private final ISysUserService userService;
    private final ISysDeptService iSysDeptService;
    private final PeispatientchargeService peispatientchargeService;

    /**
     * 查询分中心管理列表
     */
    @GetMapping("/list")
//    @PreAuthorize("@ss.hasPermi('system:branch:list')")
    @ApiOperation(value = "查询列表", notes = "查询分中心管理列表")
    public AjaxResult list(PageParam<SysBranch> page, SysBranch param) {
        return AjaxResult.success(sysBranchService.getList(page, param));
    }

    /**
     * 获取所有分中心列表
     */
    @GetMapping("/getAll")
//    @PreAuthorize("@ss.hasPermi('system:branch:list')")
    @ApiOperation(value = "获取所有分中心", notes = "获取所有分中心列表")
    public R<List<SysBranch>> getAll() {
        return R.ok(sysBranchService.list(new LambdaQueryWrapper<SysBranch>().eq(SysBranch::getIsDelete, 0)));
    }

    /**
     * 导出分中心管理列表
     */
    @PostMapping("/export")
    @PreAuthorize("@ss.hasPermi('system:branch:export')")
    @ApiOperation(value = "导出列表", notes = "导出分中心管理列表")
    @Log(title = "分中心管理-导出列表", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, SysBranch sysBranch) {
        List<SysBranch> list = new ArrayList(); // TODO 实现查询要导出数据列表 sysBranchService.getList(null, sysBranch);
        ExcelUtil<SysBranch> util = new ExcelUtil<SysBranch>(SysBranch.class);
        util.exportExcel(response, list, "分中心管理数据");
    }

    /**
     * 获取分中心管理详细信息
     */
    @GetMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPermi('system:branch:query')")
    @ApiOperation(value = "获取详细信息", notes = "获取分中心管理详细信息")
    @ApiImplicitParam(name = "id", value = "主键")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(sysBranchService.getDetailById(id));
    }

    /**
     * 新增分中心管理
     */
    @PostMapping
    @PreAuthorize("@ss.hasPermi('system:branch:add')")
    @ApiOperation(value = "新增分中心管理", notes = "新增分中心管理")
    @Log(title = "分中心管理-新增分中心管理", businessType = BusinessType.INSERT)
    public AjaxResult add(@RequestBody SysBranch sysBranch) {
        return toAjax(sysBranchService.insertNew(sysBranch));
    }

    /**
     * 修改分中心管理
     */
    @PutMapping
    @PreAuthorize("@ss.hasPermi('system:branch:edit')")
    @ApiOperation(value = "修改分中心管理", notes = "修改分中心管理")
    @Log(title = "分中心管理-修改分中心管理", businessType = BusinessType.UPDATE)
    public AjaxResult edit(@RequestBody SysBranch sysBranch) {
        return toAjax(sysBranchService.upById(sysBranch));
    }

    /**
     * 删除分中心管理
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPermi('system:branch:remove')")
    @ApiImplicitParam(name = "id", value = "主键")
    @ApiOperation(value = "删除分中心管理", notes = "删除分中心管理")
    @Log(title = "分中心管理-删除分中心管理", businessType = BusinessType.DELETE)
    public AjaxResult remove(@PathVariable List<Integer> ids) {
        return toAjax(sysBranchService.delByIds(ids));
    }


    /**
     * 删除体检号
     *
     * @param patientCode
     * @return
     */
    @DeleteMapping(value = "/deletePeispatient")
    @PreAuthorize("@ss.hasPermi('system:branch:remove')")
    @ApiOperation(value = "删除体检号", notes = "删除体检号")
    @ApiImplicitParam(name = "patientCode", value = "体检号")
    @Log(title = "删除体检号", businessType = BusinessType.DELETE)
    public R findPeispatientList(@RequestParam String patientCode) {
        return R.toResult(peispatientService.deletePeispatient(patientCode));
    }


    /**
     * 修改项目状态-查询
     *
     * @param patientCode
     * @param fExaminated
     * @return
     */
    @GetMapping(value = "/findProjectStatus")
    @PreAuthorize("@ss.hasPermi('system:branch:query')")
    @ApiOperation(value = "修改项目状态-查询", notes = "修改项目状态-查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientCode", value = "体检号"),
            @ApiImplicitParam(name = "fExaminated", value = "0：未检；1：已检")
    })
    public R<List<FindProjectStatusVo>> findProjectStatus(@RequestParam String patientCode, @RequestParam(required = false) Integer fExaminated) {
        QueryWrapper<Peispatientfeeitem> queryWrapper = new QueryWrapper<Peispatientfeeitem>();
        //去空格
        if (ObjectUtils.isNotEmpty(patientCode)) {
            patientCode = patientCode.trim();
            if (StringUtils.isNotEmpty(patientCode)) {
                queryWrapper.eq("id_patient", patientCode);
                if (ObjectUtils.isNotEmpty(fExaminated)) {
                    queryWrapper.eq("f_examinated", fExaminated);
                }
            } else {
                throw new ServiceException("体检号不能为空");
            }
        } else {
            throw new ServiceException("请输入体检号");
        }
        //实体类转vo
        List<Peispatientfeeitem> list = peispatientfeeitemService.list(queryWrapper);
        List<FindProjectStatusVo> vo = new ArrayList<>();
        for (Peispatientfeeitem peispatientfeeitem : list) {
            FindProjectStatusVo findProjectStatusVo = new FindProjectStatusVo();
            BeanUtils.copyProperties(peispatientfeeitem, findProjectStatusVo);
            SysDept dept = iSysDeptService.getByDeptNo(peispatientfeeitem.getIdKs());
            if (ObjectUtils.isNotEmpty(dept))findProjectStatusVo.setKsName(dept.getDeptName());
            vo.add(findProjectStatusVo);
        }
        return R.ok(vo);
    }


    /**
     * 修改项目状态-查询
     *
     * @param param
     * @return
     */
    @PutMapping(value = "/modifyProjectStatus")
    @PreAuthorize("@ss.hasPermi('system:branch:edit')")
    @ApiOperation(value = "修改项目状态-修改", notes = "修改项目状态-修改")
    @Log(title = "修改项目状态-修改", businessType = BusinessType.INSERT)
    public R modifyProjectStatus(@RequestBody List<FindProjectStatusVo> param) {
        List<Peispatientfeeitem> list = new ArrayList<>();
        for (FindProjectStatusVo vo : param) {
            Peispatientfeeitem peispatientfeeitem = new Peispatientfeeitem();
            BeanUtils.copyProperties(vo, peispatientfeeitem);
            list.add(peispatientfeeitem);
        }
        boolean b = peispatientfeeitemService.updateBatchById(list);
        return R.toResult(b);
    }


    /**
     * 修改健康或职业状态
     *
     * @param param
     * @return
     */
    @PutMapping(value = "/modifyPeispatientStatus")
    @PreAuthorize("@ss.hasPermi('system:branch:edit')")
    @ApiOperation(value = "修改健康或职业状态", notes = "修改健康或职业状态")
    @Log(title = "修改健康或职业状态", businessType = BusinessType.INSERT)
    public R modifyHealthStatus(@RequestBody ModifyPeiStatusParam param) {
        //去空格
        String patientCode = param.getPatientcode().trim();
        Peispatient peispatient = peispatientService.getByPatientCode(patientCode);
        if (ObjectUtils.isEmpty(peispatient)) {
            throw new ServiceException("该体检号不正确!");
        }
        //如果是总检未开始或者总检开始要解除锁定状态
        if (-1 == param.getStatus() || 0 == param.getStatus()){
            if (param.getDiseaseHealth() == 0){
                peispatient.setFFinallocked(0);
            }else {
                peispatient.setIdGuidenurse(0.0);
            }
        }
        if (param.getDiseaseHealth() == 0) {
            //健康
            peispatient.setJktjzt(param.getStatus());
        } else if (param.getDiseaseHealth() == 1) {
            //职业
            peispatient.setZytjzt(param.getStatus());
        }
        peispatientService.updateById(peispatient);
        //报告
        Report r = reportService.getOne(new QueryWrapper<Report>()
                .eq("disease_health", param.getDiseaseHealth()).eq("patientcode", patientCode));
        if (ObjectUtils.isNotEmpty(r)) {
            r.setStatus(param.getStatus());
            reportService.updateById(r);
        }
        return R.ok();
    }


    /**
     * 修改工种
     *
     * @param param
     * @return
     */
    @PutMapping(value = "/modifyWorkType")
    @ApiOperation(value = "修改工种", notes = "修改工种")
    @Log(title = "修改工种", businessType = BusinessType.INSERT)
    public R modifyWorkType(@RequestBody ModifyWorkTypeParam param) {
        Peispatient peispatient = peispatientService.getByPatientCode(param.getPatientcode());
        if (ObjectUtils.isEmpty(peispatient)) {
            throw new ServiceException("请输入正确的体检号");
        }
        peispatient.setWorktypeId(param.getWorktypeId());
        peispatient.setTrades(param.getTradesName());
        return R.ok(peispatientService.updateById(peispatient));
    }


    /**
     * 获取分中心管理详细信息
     */
    @GetMapping(value = "/getDetailByNO")
    @ApiOperation(value = "获取详细信息", notes = "获取分中心管理详细信息")
    @ApiImplicitParam(name = "branchId", value = "分中心id")
    public R getDetailByNO(String branchId) {
        return R.ok(sysBranchService.getByBranchId(branchId));
    }


    /**
     * 获取分中心管理详细信息
     */
    @PutMapping("/updateOpenDoctor")
    @ApiOperation(value = "修改开单医生", notes = "修改开单医生")
    @Log(title = "分中心管理-修改开单医生", businessType = BusinessType.UPDATE)
    public R updateOpenDoctor(@RequestBody UpdateOpenDoctorParam param) {
        if (ObjectUtils.isEmpty(param)
                || ObjectUtils.isEmpty(param.getPatientcode())
                || ObjectUtils.isEmpty(param.getUserName())) {
            throw new ServiceException("请输入参数!");
        }
        SysUser user = userService.selectUserByUserName(param.getUserName());
        if (ObjectUtils.isEmpty(user)) {
            throw new ServiceException("请确认用户名是否正确!");
        }
        Peispatient peispatient = peispatientService.getByPatientCode(param.getPatientcode());
        if (ObjectUtils.isEmpty(peispatient)) {
            throw new ServiceException("请确认体检号是否正确!");
        }
        peispatient.setIdOpendoctor(user.getUserNo());//开单医生ID
        peispatient.setDoctorapply(user.getUserName());//开单医生
        peispatientService.updateById(peispatient);
        return R.ok(Boolean.TRUE);
    }

    /**
     * 重置redis中体检号前缀：将redis缓存中的以APP开头的体检号改为当前分中心简码开头的体检号
     *
     * @param jm 分中心简码
     * @return
     */
    @GetMapping("/resetRedisPatentCode/{jm}")
    @ApiOperation(value = "重置redis中体检号前缀", notes = "将redis缓存中的以APP开头的体检号改为当前分中心简码开头的体检号)")
    @ApiImplicitParam(name = "jm", value = "分中心简码")
    public R setRedisPatentCode(@PathVariable String jm) {
        return R.toResult(CodeUtil.resetRedisPatentCode(jm));
    }

    /**
     * 获取redis中体检号列表
     *
     * @return
     */
    @GetMapping("/getRedisPatentCodes")
    @ApiOperation(value = "获取redis中体检号列表", notes = "获取redis中体检号列表")
    public R getRedisPatentCodes() {
        Set<Object> set = RedisSetUtil.queryByRange(Constants.PATIENT_CODE_SET, 0L, null);
        return R.ok(set);
    }




    /**
     * 获取分中心管理详细信息
     */
    @PutMapping("/updateMealOpenDoctor")
    @ApiOperation(value = "修改团检开单医生", notes = "修改团检开单医生")
    @Log(title = "分中心管理-修改团检开单医生", businessType = BusinessType.UPDATE)
    public R updateMealOpenDoctor(@RequestBody List<String> patientCodes) {
        for (String patientCode : patientCodes) {
            Peispatient peispatient = peispatientService.getByPatientCode(patientCode);
            if (ObjectUtils.isNotEmpty(peispatient)){
                String idTjtc = peispatient.getIdTjtc();
                Createmeal createmeal = createmealService.getInfoById(idTjtc);
                peispatient.setIdOpendoctor(createmeal.getXsjlid());//开单医生ID

                peispatient.setDoctorapply(userService.selectUserByUserNo(createmeal.getXsjlid()).getUserName());//开单医生
                peispatientService.updateById(peispatient);
            }
        }
        return R.ok(Boolean.TRUE);
    }




    /**
     * 修改工种
     *
     * @param param
     * @return
     */
    @PutMapping(value = "/modifyDepartment")
    @ApiOperation(value = "修改科室", notes = "修改科室")
    public R modifyDepartment(@Valid @RequestBody ModifyDepartmentParam param) {
        Peispatientfeeitem peispatientfeeitem= peispatientfeeitemService.getInfoById(param.getId());
        if (ObjectUtils.isEmpty(peispatientfeeitem)) {
            throw new ServiceException("请输入正确的收费项目id");
        }
        peispatientfeeitem.setIdKs(param.getKsId());
        peispatientfeeitemService.updateById(peispatientfeeitem);
        return R.ok(Boolean.TRUE);
    }




    /**
     * 登记后收费
     *
     * @param param
     * @return
     */
    @PostMapping("/changePaymentMethodByAdmin")
    @RepeatSubmit(interval = 3000, message = "正在处理，请稍等")
    @ApiOperation(value = "修改收费方式(管理员)", notes = "修改收费方式(管理员)")
    @Log(title = "前台-收费管理-更改收费方式", businessType = BusinessType.INSERT)
    public R<Boolean> changePaymentMethodByAdmin(@RequestBody @Valid ChangePaymentMethodParam param) {
        //TODO wait 实现数据库锁，保证支付信息的一致性
        return R.ok(this.peispatientchargeService.changePaymentMethodByAdmin(param));
    }
}
