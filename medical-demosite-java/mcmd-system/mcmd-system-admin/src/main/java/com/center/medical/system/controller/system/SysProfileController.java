package com.center.medical.system.controller.system;

import cn.hutool.core.io.FileUtil;
import com.center.medical.bean.enums.AttachmentType;
import com.center.medical.bean.enums.FilePathConfigFlag;
import com.center.medical.bean.model.Attachment;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.constant.UserConstants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.AjaxResult;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.core.domain.model.LoginUser;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.framework.web.service.TokenService;
import com.center.medical.service.AttachmentService;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 个人信息 业务处理
 *
 * @author 路飞
 */
@Api(tags = "个人信息")
@RestController
@RequestMapping("/system/user/profile")
public class SysProfileController extends BaseController {
    @Resource
    private ISysUserService userService;
    @Resource
    private TokenService tokenService;
    @Resource
    private AttachmentService attachmentService;
    @Resource
    private SystemConfig systemConfig;

    /**
     * 个人信息
     */
    @GetMapping
    @ApiOperation(value = "个人信息", notes = "个人信息")
    public AjaxResult profile() {
        LoginUser loginUser = getLoginUser();
        SysUser user = loginUser.getUser();
        AjaxResult ajax = AjaxResult.success(user);
        ajax.put("roleGroup", userService.selectUserRoleGroup(loginUser.getUsername()));
        ajax.put("postGroup", userService.selectUserPostGroup(loginUser.getUsername()));
        return ajax;
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息-修改用户", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改用户", notes = "修改用户")
    public AjaxResult updateProfile(@RequestBody SysUser user) {
        LoginUser loginUser = getLoginUser();
        SysUser sysUser = loginUser.getUser();
        user.setUserName(sysUser.getUserName());
        if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUserId(sysUser.getUserId());
        user.setPassword(null);
        user.setAvatar(null);
        user.setDeptId(null);
        if (userService.updateUserProfile(user) > 0) {
            // 更新缓存用户信息
            sysUser.setNickName(user.getNickName());
            sysUser.setPhonenumber(user.getPhonenumber());
            sysUser.setEmail(user.getEmail());
            sysUser.setSex(user.getSex());
            tokenService.setLoginUser(loginUser);
            return AjaxResult.success();
        }
        return AjaxResult.error("修改个人信息异常，请联系管理员");
    }

    /**
     * 重置密码
     */
    @Log(title = "个人信息-重置密码", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    @ApiOperation(value = "重置密码", notes = "重置密码")
    public AjaxResult updatePwd(String oldPassword, String newPassword) {
        LoginUser loginUser = getLoginUser();
        String userName = loginUser.getUsername();
        String password = loginUser.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password)) {
            return AjaxResult.error("修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(newPassword, password)) {
            return AjaxResult.error("新密码不能与旧密码相同");
        }
        if (userService.resetUserPwd(userName, SecurityUtils.encryptPassword(newPassword)) > 0) {
            // 更新缓存用户密码
            loginUser.getUser().setPassword(SecurityUtils.encryptPassword(newPassword));
            tokenService.setLoginUser(loginUser);
            return AjaxResult.success();
        }
        return AjaxResult.error("修改密码异常，请联系管理员");
    }

    /**
     * 头像上传
     */
    @Log(title = "用户头像-头像上传", businessType = BusinessType.UPDATE)
    @PostMapping("/avatar")
    @ApiOperation(value = "头像上传", notes = "头像上传")
    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            LoginUser loginUser = getLoginUser();
            // 上传文件路径
            Attachment attachment = new Attachment();
            String extName = FileUtil.extName(file.getOriginalFilename());
            attachment.setFileType(extName);
            //TODO wait等待完善文件类型判断
            attachment.setType(AttachmentType.IMAGE.value());
            attachment.setBranchId(SecurityUtils.getCId());
            attachment.setCreatedate(new Date());
            String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.CPP.value());
            attachmentService.uploadFile(file, attachment, baseDir, extName, null, true);
//            String avatar = FileUploadUtils.upload(ZhongkangConfig.getAvatarPath(), file, MimeTypeUtils.IMAGE_EXTENSION);
            String avatar = attachment.getFilePath();
            if (userService.updateUserAvatar(loginUser.getUsername(), avatar)) {
                AjaxResult ajax = AjaxResult.success();
                ajax.put("imgUrl", avatar);
                // 更新缓存用户头像
                loginUser.getUser().setAvatar(avatar);
                tokenService.setLoginUser(loginUser);
                return ajax;
            }
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }
}
