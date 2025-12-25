package com.center.medical.member.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.member.bean.model.Satisfaction;
import com.center.medical.member.bean.param.SatisfactionParam;
import com.center.medical.member.bean.param.WpjDataParam;
import com.center.medical.member.bean.vo.AddSatisficingVo;
import com.center.medical.member.service.SatisfactionService;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.bean.model.SysDepartment;
import com.center.medical.system.service.BranchService;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.SysDepartmentService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * KF满意度(Satisfaction)表控制层
 *
 * @author ay
 * @since 2022-11-28 11:23:43
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "满意度管理-体检满意度")
@RequestMapping("/addSatisficing")
public class AddSatisficingController extends BaseController {
    /**
     * 服务对象
     */
    private final SatisfactionService satisfactionService;
    private final MapperFacade mapperFacade;
    private final SysDepartmentService sysDepartmentService;
    private final PeispatientService peispatientService;
    private final BranchService branchService;
    private final ISysBranchService iSysBranchService;

    /**
     * 分页查询体检满意度所有数据
     *
     * @param pageParamSimple   分页参数
     * @param satisfactionParam 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询体检满意度所有数据")
    public R<IPage<AddSatisficingVo>> getPage(PageParamSimple pageParamSimple, SatisfactionParam satisfactionParam) {
        PageParam<AddSatisficingVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.satisfactionService.getAddSatisficingPage(page, satisfactionParam));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查KF满意度详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<Satisfaction> selectOne(@PathVariable String id) {
        return R.ok(this.satisfactionService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param satisfaction 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "保存", notes = "客户满意度录入")
    @ApiOperationSupport(ignoreParameters = {"satisfaction.id"})
    public R saOrUp(@RequestBody Satisfaction satisfaction) {
        return R.toResult(this.satisfactionService.saOrUpAdd(satisfaction));
    }


    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除KF满意度")
    @Log(title = "KF满意度", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        //修改为已删除状态
        Satisfaction satisfaction = new Satisfaction();
        satisfaction.setIsDelete(1);
        boolean b = satisfactionService.update(satisfaction, new UpdateWrapper<Satisfaction>().in("id", ids));
        return R.toResult(b);
    }


    /**
     * 返回编辑数据
     *
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "返回编辑数据", notes = "返回编辑数据")
    public R edit(@PathVariable String id) {
        Map map = new HashMap();
        Satisfaction satisfaction = satisfactionService.getInfoById(id);
        if (ObjectUtils.isNotEmpty(satisfaction)) {
            // 由评价科室的id，得到科室name
            SysDepartment sysDepartment = sysDepartmentService.getInfoById(satisfaction.getDivisionId());
            map.put("SysDepartment", sysDepartment);
            // 由体检号得到体检者表里面的个人信息
            Peispatient patientcode = peispatientService.getOne(new QueryWrapper<Peispatient>().eq("patientcode", satisfaction.getPersoncode()));
            map.put("Peispatient", patientcode);
        }
        map.put("Satisfaction", satisfaction);
        return R.ok(map);
    }


    /**
     * 获取体检者信息
     *
     * @param key
     * @return
     */
    @GetMapping("/getTjzData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取体检者信息", notes = "获取体检者信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "personcode", value = "体检号"),
            @ApiImplicitParam(name = "key", value = "是否补0 ，true是false否")
    })
    public R getTjzData(@RequestParam(value = "personcode") String personcode,
                        @RequestParam(value = "key", required = false) String key) {
        if (StringUtils.isNotEmpty(key) && "true".equals(key)) {
            personcode = ToolUtil.patientCode(personcode, iSysBranchService.getBranchFlag(null));
        }
        //已登记
        Peispatient peispatient = peispatientService.getOne(new QueryWrapper<Peispatient>().eq("patientcode", personcode).eq("f_registered", 1));
        if (ObjectUtils.isEmpty(peispatient)) {
            throw new ServiceException("未查询到体检者信息");
        }
        Peispatient patientcode = peispatientService.getOne(new QueryWrapper<Peispatient>().eq("patientcode", personcode));
        return R.ok(patientcode);
    }


    /**
     * 导出
     *
     * @param response
     * @param satisfactionParam
     */
    @PostMapping("/export")
    @ApiOperation(value = "导出", notes = "导出")
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @Log(title = "导出", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, SatisfactionParam satisfactionParam) {
        List<AddSatisficingVo> list = satisfactionService.getAllAddList(satisfactionParam);
        ExcelUtil<AddSatisficingVo> util = new ExcelUtil<AddSatisficingVo>(AddSatisficingVo.class);
        util.exportExcel(response, list, "会员列表数据");
    }


    /**
     * 显示未评价的数据
     *
     * @param pageParamSimple
     * @param wpjDataParam
     * @return
     */
    @GetMapping("/getWpjData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页显示未评价的数据", notes = "分页显示未评价的数据")
    public R<IPage<Peispatient>> getWpjData(PageParamSimple pageParamSimple, WpjDataParam wpjDataParam) {
        PageParam<Peispatient> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.satisfactionService.getWpjData(page, wpjDataParam));
    }


    /**
     * 导出未评价数据导出
     *
     * @param response
     * @param wpjDataParam
     */
    @PostMapping("/exportWpjData")
    @ApiOperation(value = "未评价数据导出", notes = "未评价数据导出")
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @Log(title = "导出", businessType = BusinessType.EXPORT)
    public void exportWpjData(HttpServletResponse response, WpjDataParam wpjDataParam) {
        List<Peispatient> list = satisfactionService.getAllWpjData(wpjDataParam);
        ExcelUtil<Peispatient> util = new ExcelUtil<Peispatient>(Peispatient.class);
        util.exportExcel(response, list, "会员列表数据");
    }




}

