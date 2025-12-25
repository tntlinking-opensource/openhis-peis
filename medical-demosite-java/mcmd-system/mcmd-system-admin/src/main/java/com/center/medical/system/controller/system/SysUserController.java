package com.center.medical.system.controller.system;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.constant.UserConstants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.AjaxResult;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.core.domain.entity.SysRole;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.data.service.HarmService;
import com.center.medical.data.service.ZyHarmClassService;
import com.center.medical.system.bean.dto.IdNameDto;
import com.center.medical.system.bean.model.KdSaleer;
import com.center.medical.system.bean.model.SysUserBranch;
import com.center.medical.system.bean.model.SysUserDep;
import com.center.medical.system.bean.model.UserHarmClass;
import com.center.medical.system.bean.param.KdSaleerParam;
import com.center.medical.system.bean.param.SysUserParam;
import com.center.medical.system.bean.vo.AllUserDataVo;
import com.center.medical.system.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户信息
 *
 * @author 路飞
 */
@Slf4j
@Api(tags = "用户信息")
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {
    @Resource
    private ISysMenuService menuService;
    @Resource
    private ISysUserService userService;
    @Resource
    private ISysRoleService roleService;
    @Resource
    private ISysDeptService deptService;
    @Resource
    private ISysPostService postService;
    @Resource
    private KdSaleerService kdSaleerService;
    @Resource
    private MapperFacade mapperFacade;
    @Resource
    private UserHarmClassService userHarmClassService;
    @Resource
    private SysUserBranchService sysUserBranchService;
    @Resource
    private HarmService harmService;
    @Resource
    private SysUserDepService sysUserDepService;
    @Resource
    private ISysDeptService sysDeptService;
    @Resource
    private ISysBranchService iSysBranchService;
    @Resource
    private ZyHarmClassService zyHarmClassService;


    /**
     * 获取用户列表
     */
//    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取用户列表", notes = "根据查询条件获取用户列表")
    public R list(PageParamSimple pageParamSimple, SysUser user) {
        PageParam<SysUser> page = mapperFacade.map(pageParamSimple, PageParam.class);
        List<SysUser> list = userService.selectUserList(user);
        IPage<SysUser> iPage= ToolUtil.getPages((int) page.getCurrent(), (int) page.getSize(), list);
        return R.ok(iPage);
    }

    /**
     * 根据输入码获取系统用户
     *
     * @param key 系统用户输入码
     * @return
     */
    @GetMapping("/getListByKey")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "根据输入码获取系统用户", notes = "根据输入码获取系统用户")
    @ApiImplicitParam(name = "key", value = "系统用户输入码")
    public R<List<AllUserDataVo>> getListByKey(String key) {
        List<AllUserDataVo> list = userService.getAllUserData(key);
        return R.ok(list);
    }

    @Log(title = "用户管理-导出用户数据", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @PostMapping("/export")
    @ApiOperation(value = "导出用户数据", notes = "导出Excel用户数据")
    public void export(HttpServletResponse response, SysUser user) {
        List<SysUser> list = userService.selectUserList(user);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.exportExcel(response, list, "用户数据");
    }

    @Log(title = "用户管理-导入用户数据", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/importData")
    @ApiOperation(value = "导入用户数据", notes = "导入Excel用户数据")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = userService.importUser(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    @ApiOperation(value = "下载用户数据模板", notes = "下载用户数据Excel模板")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.importTemplateExcel(response, "用户数据");
    }

    /**
     * 根据用户编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = {"/", "/{userId}"})
    @ApiOperation(value = "查询用户信息", notes = "根据用户编号获取详细信息")
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId) {

        logger.info("用户ID：" + userId);
        userService.checkUserDataScope(userId);
        AjaxResult ajax = AjaxResult.success();
        List<SysRole> roles = roleService.selectRoleAll();
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        ajax.put("posts", postService.selectPostAll());
        if (Objects.nonNull(userId)) {
            SysUser sysUser = userService.selectUserById(userId);
            userService.checkUserAllowed(sysUser);
            //上级名称
            if (ObjectUtils.isNotEmpty(sysUser.getSuperiorId())) {
                SysUser sysUser1 = userService.selectUserByUserNo(sysUser.getSuperiorId());
                if (ObjectUtils.isNotEmpty(sysUser1))ajax.put("superior", sysUser1.getUserName());
            }
            sysUser.setReciveCode(null);

            ajax.put("postIds", postService.selectPostListByUserId(userId));
            ajax.put("roleIds", sysUser.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toList()));
            //职业危害因素
            List<UserHarmClass> harmClasses = userHarmClassService.list(new LambdaQueryWrapper<UserHarmClass>().eq(UserHarmClass::getUserId, sysUser.getUserNo()));
            List<IdNameDto> harmList = new ArrayList<>();
            for (UserHarmClass harmClass : harmClasses) {
                harmList.add(new IdNameDto(harmClass.getClassId(), zyHarmClassService.getInfoById(harmClass.getClassId()).getName()));
            }
            ajax.put("harmClass", harmList);
            //科室
            List<SysUserDep> sysUserDeps = sysUserDepService.list(new LambdaQueryWrapper<SysUserDep>().eq(SysUserDep::getUserId, sysUser.getUserNo()));
            List<IdNameDto> deptList = new ArrayList<>();
            for (SysUserDep sysUserDep : sysUserDeps) {
                SysDept dept = sysDeptService.getByDeptNo(sysUserDep.getDepId());
                if (ObjectUtils.isNotEmpty(dept)) {
                    deptList.add(new IdNameDto(sysUserDep.getDepId(),dept.getDeptName()));
                }

            }
            ajax.put("deptList", deptList);
            //兼职中心
            List<SysUserBranch> useBranches = sysUserBranchService.list(new LambdaQueryWrapper<SysUserBranch>().eq(SysUserBranch::getUserId, sysUser.getUserNo()));
            List<IdNameDto> useBranchList = new ArrayList<>();
            for (SysUserBranch sysUserBranch : useBranches) {
                useBranchList.add(new IdNameDto(sysUserBranch.getBranchId(), iSysBranchService.getByBranchId(sysUserBranch.getBranchId()).getFzx()));
            }
            ajax.put("useBranches", useBranchList);
            //默认分中心
            SysBranch sysBranch = iSysBranchService.getByBranchId(sysUser.getCid());
            sysUser.setFzxmc(sysBranch.getFzx());
            ajax.put(AjaxResult.DATA_TAG, sysUser);

        }
        return ajax;
    }

    /**
     * 新增用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "用户管理-新增用户", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "新增用户", notes = "新增用户")
    public AjaxResult add(@Validated @RequestBody SysUser user) {
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setCreateBy(getUsername());
        if (StringUtils.isBlank(user.getCid())) {
            user.setCid(SecurityUtils.getCId());
        }
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理-修改用户", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改用户", notes = "修改用户")
    public AjaxResult edit(@Validated @RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUpdateBy(getUsername());
        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:remove')")
    @Log(title = "用户管理-删除用户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    @ApiOperation(value = "删除用户", notes = "根据用户id列表删除用户")
    public AjaxResult remove(@PathVariable Long[] userIds) {
        if (ArrayUtils.contains(userIds, getUserId())) {
            return error("当前用户不能删除");
        }
        return toAjax(userService.deleteUserByIds(userIds));
    }

    /**
     * 重置密码
     */
    @PreAuthorize("@ss.hasPermi('system:user:resetPwd')")
    @Log(title = "用户管理-重置密码", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    @ApiOperation(value = "重置密码", notes = "重置密码")
    public AjaxResult resetPwd(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateBy(getUsername());
        return toAjax(userService.resetPwd(user));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理-状态修改", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    @ApiOperation(value = "状态修改", notes = "状态修改")
    public AjaxResult changeStatus(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        user.setUpdateBy(getUsername());
        return toAjax(userService.updateUserStatus(user));
    }

    /**
     * 根据用户编号获取授权角色
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping("/authRole/{userId}")
    @ApiOperation(value = "获取用户授权角色", notes = "根据用户编号获取授权角色")
    public AjaxResult authRole(@PathVariable("userId") Long userId) {
        AjaxResult ajax = AjaxResult.success();
        SysUser user = userService.selectUserById(userId);
        List<SysRole> roles = roleService.selectRolesByUserId(userId);
        ajax.put("user", user);
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        return ajax;
    }

    /**
     * 用户授权角色
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理-用户授权角色", businessType = BusinessType.GRANT)
    @PutMapping("/authRole")
    @ApiOperation(value = "保存用户授权角色", notes = "保存用户授权角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID"),
            @ApiImplicitParam(name = "roleIds", value = "授权的角色Id列表")
    })
    public AjaxResult insertAuthRole(Long userId, Long[] roleIds) {
        userService.checkUserDataScope(userId);
        userService.insertUserAuth(userId, roleIds);
        return success();
    }

    /**
     * 获取部门树列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/deptTree")
    @ApiOperation(value = "获取部门树列表", notes = "获取部门树列表")
    public AjaxResult deptTree(SysDept dept) {
        return AjaxResult.success(deptService.selectDeptTreeList(dept));
    }

    /**
     * 获取金蝶账户数据
     */
    @GetMapping("/getKingdeeData")
    @ApiImplicitParam(name = "key", value = "名字")
    @ApiOperation(value = "获取金蝶账户数据", notes = "获取金蝶账户数据")
    public R<IPage<KdSaleer>> getKingdeeData(PageParamSimple pageParamSimple, @RequestParam(value = "key", required = false) String key) {
        PageParam<KdSaleer> page = mapperFacade.map(pageParamSimple, PageParam.class);
        KdSaleerParam param = new KdSaleerParam();
        param.setAccountName(key);
        return R.ok(kdSaleerService.getList(page, param));
    }

    /**
     * 生成用户分中心关联关系
     */
    @GetMapping("/generateUserBranch")
    @ApiOperation(value = "用户关联分中心", notes = "生成用户分中心关联关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userNos", value = "用户编号集合"),
            @ApiImplicitParam(name = "cid", value = "关联的分中心ID")
    })
    public R<String> generateUserBranch(@RequestParam(value = "userNos", required = false) List<String> userNos, @RequestParam(value = "cid", required = false) String cid) {

        List<SysUserBranch> userBranches = new ArrayList<>();

        if (CollectionUtil.isEmpty(userNos) && StringUtils.isBlank(cid)) {
            //为所有用户生成关联关系
            List<SysUser> allData = userService.getAllData(new SysUserParam());
            for (SysUser user : allData) {
                SysUserBranch userBranch = new SysUserBranch();
                if (StringUtils.isNotBlank(user.getCid())) {
                    userBranch.setBranchId(user.getCid());
                    userBranch.setUserId(user.getUserNo());
                    userBranches.add(userBranch);
                }
            }
        } else if (CollectionUtil.isNotEmpty(userNos) && StringUtils.isBlank(cid)) {
            for (String userNo : userNos) {
                SysUserBranch userBranch = new SysUserBranch();
                SysUser user = userService.getUserByNo(userNo);
                if (Objects.isNull(user) || StringUtils.isBlank(user.getCid())) {
                    continue;
                }
                userBranch.setBranchId(user.getCid());
                userBranch.setUserId(userNo);
                userBranches.add(userBranch);
            }
        } else if (CollectionUtil.isNotEmpty(userNos) && StringUtils.isNotBlank(cid)) {
            for (String userNo : userNos) {
                SysUserBranch userBranch = new SysUserBranch();
                userBranch.setBranchId(cid);
                userBranch.setUserId(userNo);
                userBranches.add(userBranch);
            }
        }
        if (CollectionUtil.isNotEmpty(userBranches)) {
            for (SysUserBranch item : userBranches) {
                //判断是否存在关联关系
                if (sysUserBranchService.count(new LambdaQueryWrapper<SysUserBranch>().eq(SysUserBranch::getUserId, item.getUserId())
                        .eq(SysUserBranch::getBranchId, item.getBranchId())) == 0) {
                    log.info("生成关联关系：{}", item);
                    sysUserBranchService.save(item);
                } else {
                    log.info("已存在关联关系：{}", item);
                }
            }
        }
        return R.ok("关联成功！");
    }


    /**
     * 更新用户和金蝶关联表
     */
    @PutMapping("/updateUserSaleer")
    @ApiOperation(value = "更新用户和金蝶关联表", notes = "更新用户和金蝶关联表，有时候md5会变")
    @ApiImplicitParam(name = "cid", value = "分中心")
    public R<Boolean> updateUserSaleer(String cid) {
        Boolean b = kdSaleerService.updateUserSaleer(cid);
        return R.ok(b);
    }
}
