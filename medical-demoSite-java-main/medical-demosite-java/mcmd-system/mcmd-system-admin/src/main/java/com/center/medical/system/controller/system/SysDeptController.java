package com.center.medical.system.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.constant.UserConstants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.AjaxResult;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.system.bean.model.KdDepartment;
import com.center.medical.system.bean.param.KdDepartmentParam;
import com.center.medical.system.bean.param.SysDeptParam;
import com.center.medical.system.bean.vo.DeptSimpleVo;
import com.center.medical.system.service.ISysDeptService;
import com.center.medical.system.service.KdDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部门信息
 *
 * @author 路飞
 */
@Api(tags = "部门信息")
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {
    @Resource
    private ISysDeptService deptService;
    @Resource
    private MapperFacade mapperFacade;
    @Resource
    private KdDepartmentService kdDepartmentService;

    /**
     * 获取部门列表
     */
    @GetMapping("/list")
//    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @ApiOperation(value = "获取部门列表", notes = "获取部门列表")
    public AjaxResult list(SysDept dept) {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return AjaxResult.success(depts);
    }

    /**
     * 查询部门列表（排除节点）
     */
    @GetMapping("/list/exclude/{deptId}")
//    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @ApiOperation(value = "查询部门列表（排除节点）", notes = "查询部门列表（排除节点）")
    public AjaxResult excludeChild(@PathVariable(value = "deptId", required = false) Long deptId) {
        List<SysDept> depts = deptService.selectDeptList(new SysDept());
        depts.removeIf(d -> d.getDeptId().intValue() == deptId || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + ""));
        return AjaxResult.success(depts);
    }

    /**
     * 根据部门编号获取详细信息
     */
    @GetMapping(value = "/{deptId}")
//    @PreAuthorize("@ss.hasPermi('system:dept:query')")
    @ApiOperation(value = "部门详情", notes = "根据部门编号获取详细信息")
    public AjaxResult getInfo(@PathVariable Long deptId) {
        deptService.checkDeptDataScope(deptId);
        return AjaxResult.success(deptService.selectDeptById(deptId));
    }

    /**
     * 新增部门
     */
    @PostMapping
    @PreAuthorize("@ss.hasPermi('system:dept:add')")
    @Log(title = "部门管理-新增部门", businessType = BusinessType.INSERT)
    @ApiOperation(value = "新增部门", notes = "新增部门")
    public AjaxResult add(@Validated @RequestBody SysDept dept) {
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return AjaxResult.error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        dept.setCreateBy(getUsername());
        return toAjax(deptService.insertDept(dept));
    }

    /**
     * 修改部门
     */
    @PutMapping
    @PreAuthorize("@ss.hasPermi('system:dept:edit')")
    @Log(title = "部门管理-修改部门", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "修改部门", notes = "修改部门")
    public AjaxResult edit(@Validated @RequestBody SysDept dept) {
        Long deptId = dept.getDeptId();
        deptService.checkDeptDataScope(deptId);
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return AjaxResult.error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        } else if (dept.getParentId().equals(deptId)) {
            return AjaxResult.error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        } else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus()) && deptService.selectNormalChildrenDeptById(deptId) > 0) {
            return AjaxResult.error("该部门包含未停用的子部门！");
        }
        dept.setUpdateBy(getUsername());
        return toAjax(deptService.updateDept(dept));
    }


    /**
     * 批量修改部门
     */
    @PutMapping("update")
    @PreAuthorize("@ss.hasPermi('system:dept:edit')")
    @Log(title = "部门管理-批量修改部门", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "批量修改部门", notes = "批量修改部门")
    public AjaxResult update(@Validated @RequestBody List<SysDept> list) {

        return toAjax(deptService.updateDeptList(list));
    }

    /**
     * 删除部门
     */
    @DeleteMapping("/{deptId}")
    @PreAuthorize("@ss.hasPermi('system:dept:remove')")
    @Log(title = "部门管理-删除部门", businessType = BusinessType.DELETE)
    @ApiOperation(value = "删除部门", notes = "删除部门")
    public AjaxResult remove(@PathVariable Long deptId) {
        if (deptService.hasChildByDeptId(deptId)) {
            return AjaxResult.error("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId)) {
            return AjaxResult.error("部门存在用户,不允许删除");
        }
        deptService.checkDeptDataScope(deptId);
        return toAjax(deptService.deleteDeptById(deptId));
    }

    /**
     * 获取所有的科室
     *
     * @param param 筛选条件
     * @return
     */
    // @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @GetMapping("/getAllDepartment")
    @ApiOperation(value = "获取所有的科室", notes = "获取所有的科室")
    public R<List<DeptSimpleVo>> list(@RequestBody SysDeptParam param) {

        LambdaQueryWrapper<SysDept> wrapper = new LambdaQueryWrapper<SysDept>().eq(SysDept::getIsFunction, 1).eq(SysDept::getDelFlag, 0);
        if (StringUtils.isNotBlank(param.getInputCode())) {
            wrapper.like(SysDept::getInputCode, param.getInputCode());
        }
        return R.ok(deptService.getAllDepartment(param));
    }

    /**
     * 获取金蝶账户数据
     */
    @GetMapping("/getKingdeeData")
    @ApiImplicitParam(name = "key", value = "名字")
    @ApiOperation(value = "获取金蝶账户数据", notes = "获取金蝶账户数据")
    public R<IPage<KdDepartment>> getKingdeeData(PageParamSimple pageParamSimple, @RequestParam(value = "key", required = false) String key) {
        PageParam<KdDepartment> page = mapperFacade.map(pageParamSimple, PageParam.class);
        KdDepartmentParam param = new KdDepartmentParam();
        param.setAccountName(key);
        param.setBranchId(SecurityUtils.getCId());
        return R.ok(kdDepartmentService.getPage(page, param));
    }

}
