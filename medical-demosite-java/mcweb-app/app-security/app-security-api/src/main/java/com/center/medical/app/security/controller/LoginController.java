package com.center.medical.app.security.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.app.bean.enums.SendType;
import com.center.medical.app.bean.model.User;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.common.util.PrincipalUtil;
import com.center.medical.app.dao.UserMapper;
import com.center.medical.app.security.common.bo.UserInfoInTokenBO;
import com.center.medical.app.security.common.dto.AuthenticationDTO;
import com.center.medical.app.security.common.enums.SysTypeEnum;
import com.center.medical.app.security.common.manager.PasswordCheckManager;
import com.center.medical.app.security.common.manager.TokenStore;
import com.center.medical.app.security.common.model.AppConnect;
import com.center.medical.app.security.common.service.AppConnectService;
import com.center.medical.app.security.common.vo.TokenInfoVO;
import com.center.medical.app.security.dto.SocialAuthenticationDTO;
import com.center.medical.app.service.SmsLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author FrozenWatermelon
 * @date 2020/6/30
 */
@RestController
@AllArgsConstructor
@Api(tags = "登录")
public class LoginController {

    private final TokenStore tokenStore;
    private final UserMapper userMapper;
    private final AppConnectService appConnectService;
    private final PasswordCheckManager passwordCheckManager;
    private final SmsLogService smsLogService;


    @PostMapping("/login")
    @ApiOperation(value = "账号密码(用于前端登录)", notes = "通过账号/手机号/用户名密码登录，还要携带用户的类型，也就是用户所在的系统")
    public ResponseEntity<TokenInfoVO> login(
            @Valid @RequestBody AuthenticationDTO authenticationDTO) {
        String mobileOrUserName = authenticationDTO.getPrincipal();
        User user = getUser(mobileOrUserName);

        // 半小时内密码输入错误十次，已限制登录30分钟
        passwordCheckManager.checkPassword(SysTypeEnum.ORDINARY, authenticationDTO.getPrincipal(), authenticationDTO.getCredentials(), user.getLoginPassword());
        UserInfoInTokenBO userInfoInToken = new UserInfoInTokenBO();
        userInfoInToken.setUserId(user.getUserId());
        userInfoInToken.setSysType(SysTypeEnum.ORDINARY.value());
        userInfoInToken.setEnabled(user.getStatus() == 1);
        // 存储token返回vo
        TokenInfoVO tokenInfoVO = tokenStore.storeAndGetVo(userInfoInToken);
        return ResponseEntity.ok(tokenInfoVO);
    }


    @PostMapping("/tempSocialLogin")
    @ApiOperation(value = "临时的社交登录", notes = "微信公众号支付需要openid，但用户又不绑定社交账号，所以这个openId是临时的")
    public ResponseEntity<TokenInfoVO> tempSocialLogin(
            @Valid @RequestBody SocialAuthenticationDTO authenticationDTO) {

        User user = getUser(authenticationDTO.getPrincipal());

        // 半小时内密码输入错误十次，已限制登录30分钟
        passwordCheckManager.checkPassword(SysTypeEnum.ORDINARY, authenticationDTO.getPrincipal(), authenticationDTO.getCredentials(), user.getLoginPassword());

        AppConnect appConnect = appConnectService.getByTempUid(authenticationDTO.getTempUid());

        UserInfoInTokenBO userInfoInToken = new UserInfoInTokenBO();
        userInfoInToken.setUserId(user.getUserId());
        userInfoInToken.setSysType(SysTypeEnum.ORDINARY.value());
        userInfoInToken.setEnabled(user.getStatus() == 1);
        userInfoInToken.setBizUid(appConnect.getBizUnionid());
        userInfoInToken.setBizUserId(appConnect.getBizUserId());
        userInfoInToken.setSessionKey(appConnect.getBizTempSession());
        userInfoInToken.setSocialType(appConnect.getAppId());
        // 存储token返回vo
        TokenInfoVO tokenInfoVO = tokenStore.storeAndGetVo(userInfoInToken);
        return ResponseEntity.ok(tokenInfoVO);
    }

    @PostMapping("/smsLogin")
    @ApiOperation(value = "短信登录", notes = "短信登录")
    public ResponseEntity<TokenInfoVO> smsLogin(
            @Valid @RequestBody SocialAuthenticationDTO authenticationDTO) {

        // 验证码登录
        boolean validCode;
        try {
            validCode = smsLogService.checkValidCode(authenticationDTO.getPrincipal(), String.valueOf(authenticationDTO.getCredentials()), SendType.LOGIN);
        } catch (AppBindException e) {
            // 验证码校验过频繁，请稍后再试
            throw new AppBindException("验证码校验过频繁，请稍后再试");
        }
        if (!validCode) {
            // 验证码有误或已过期
            throw new AppBindException("验证码有误或已过期");
        }

        User user = getUser(authenticationDTO.getPrincipal());

        UserInfoInTokenBO userInfoInToken = new UserInfoInTokenBO();
        userInfoInToken.setUserId(user.getUserId());
        userInfoInToken.setSysType(SysTypeEnum.ORDINARY.value());
        userInfoInToken.setEnabled(user.getStatus() == 1);
        // 存储token返回vo
        TokenInfoVO tokenInfoVO = tokenStore.storeAndGetVo(userInfoInToken);
        return ResponseEntity.ok(tokenInfoVO);
    }

    private User getUser(String mobileOrUserName) {
        User user = null;
        // 手机验证码登陆，或传过来的账号很像手机号
        if (PrincipalUtil.isMobile(mobileOrUserName)) {
            user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserMobile, mobileOrUserName));
        }
        // 如果不是手机验证码登陆， 找不到手机号就找用户名
        if (user == null) {
            user = userMapper.selectOneByUserName(mobileOrUserName);
        }
        if (user == null) {
            throw new AppBindException("用户不存在或者已被删除！");
        }
        return user;
    }


}
