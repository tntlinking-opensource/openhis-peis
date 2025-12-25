package com.center.medical.system.controller.system;

import com.center.medical.common.config.BackgroundConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.AjaxResult;
import com.center.medical.common.core.domain.entity.SysMenu;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.core.domain.model.LoginBody;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.framework.web.service.SysLoginService;
import com.center.medical.framework.web.service.SysPermissionService;
import com.center.medical.system.bean.vo.CenterListDataVo;
import com.center.medical.system.bean.vo.LogUsersVo;
import com.center.medical.system.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 登录验证
 *
 * @author 路飞
 */
@Slf4j
@Api(tags = "登录验证")
@RestController
public class SysLoginController {
    @Resource
    private SysLoginService loginService;

    @Resource
    private ISysMenuService menuService;

    @Resource
    private SysPermissionService permissionService;

    @Resource
    private ISysBranchService sysBranchService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private SysNotificationService sysNotificationService;

    @Resource
    private ISysConfigService iSysConfigService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    public AjaxResult getInfo() {
        log.info("获取用户信息");
        SysUser user = SecurityUtils.getLoginUser().getUser();
        log.info("获取用户信息user：{}", user);
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        // 获取未读的消息
        Long unreadMsg = sysNotificationService.getUnreadMsg(user.getUserNo());
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        ajax.put("unreadMsg", unreadMsg);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    @ApiOperation(value = "获取路由信息", notes = "获取路由信息")
    public AjaxResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        AjaxResult success = AjaxResult.success(menuService.buildMenus(menus));
//        log.info("路由信息{}", JSONUtil.toJsonStr(success));
        return AjaxResult.success(menuService.buildMenus(menus));
    }


    /**
     * 登录页面分中心数据
     *
     * @return
     */
    @GetMapping("/centerListData")
    @ApiOperation(value = "登录页面分中心数据", notes = "登录页面分中心数据")
    public AjaxResult centerListData() {
        List<CenterListDataVo> list = sysBranchService.centerListData();
        return AjaxResult.success(list);
    }


    /**
     * 根据名称或输入码获取登录用户名
     *
     * @param key
     * @return
     */
    @GetMapping("/getLogUsers")
    @ApiOperation(value = "根据名称或输入码获取登录用户名", notes = "根据名称或输入码获取登录用户名")
    @ApiImplicitParam(name = "key", value = "搜索条件")
    public AjaxResult getLogUsers(String key) {
        List<LogUsersVo> list = sysUserService.getLogUsers(key);
        return AjaxResult.success(list);
    }



    /**
     * 获取科室数据范围配置
     * @return
     */
    @GetMapping("/getBackgroundConfig")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "获取背景设置", notes = "获取背景设置")
    public AjaxResult getBackgroundConfig() {
        BackgroundConfig deptDataConfig = iSysConfigService.getSysConfigObject(Constants.BACKGROUND_CONFIG, BackgroundConfig.class);
        return AjaxResult.success(deptDataConfig);
    }

}
