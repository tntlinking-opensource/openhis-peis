/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.controller;


import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.center.medical.app.bean.enums.SendType;
import com.center.medical.app.bean.model.User;
import com.center.medical.app.bean.param.CheckRegisterSmsParam;
import com.center.medical.app.bean.param.SendSmsParam;
import com.center.medical.app.bean.param.UserPwdUpdateParam;
import com.center.medical.app.bean.param.UserRegisterParam;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.common.util.IPHelper;
import com.center.medical.app.common.util.Json;
import com.center.medical.app.common.util.PrincipalUtil;
import com.center.medical.app.common.util.RedisUtil;
import com.center.medical.app.config.WxConfig;
import com.center.medical.app.security.common.bo.UserInfoInTokenBO;
import com.center.medical.app.security.common.enums.SocialType;
import com.center.medical.app.security.common.enums.SysTypeEnum;
import com.center.medical.app.security.common.manager.TokenStore;
import com.center.medical.app.security.common.model.AppConnect;
import com.center.medical.app.security.common.service.AppConnectService;
import com.center.medical.app.security.common.vo.TokenInfoVO;
import com.center.medical.app.service.SmsLogService;
import com.center.medical.app.service.UserService;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户信息
 *
 * @author LGH
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "用户注册相关接口")
@AllArgsConstructor
public class UserRegisterController {

    private final WxConfig wxConfig;
    private final UserService userService;
    private final MapperFacade mapperFacade;
    private final SmsLogService smsLogService;
    private final AppConnectService appConnectService;
    private final TokenStore tokenStore;
    private final PasswordEncoder passwordEncoder;
    public static final String CHECK_REGISTER_SMS_FLAG = "checkRegisterSmsFlag";
    public static final String CHECK_UPDATE_PWD_SMS_FLAG = "updatePwdSmsFlag";


    /**
     * 注册的头像昵称有几个值得注意的地方：
     * 1. 如果是微信公众号 or 小程序注册，在注册之前，也就是在进入页面之前，需要调用SocialLoginController 这里面的尝试登录的接口，如果可以登录就直接登录了。
     * 2. 关于发送验证码
     * 1) 手机号注册,要发送验证码
     * 3. 当注册成功的时候，已经返回token，相对来说，已经登录了
     */
    @ApiOperation(value = "注册", notes = "注册的头像昵称有几个值得注意的地方：" +
            "1. 如果是微信公众号 or 小程序注册，在注册之前，也就是在进入页面之前，需要调用SocialLoginController 这里面的尝试登录的接口，如果可以登录就直接登录了。" +
            "2. 关于发送验证码：手机号注册,要发送验证码" +
            "3. 当注册成功的时候，已经返回token，相对来说，已经登录了")
    @PostMapping("/register")
    public ResponseEntity<TokenInfoVO> register(@Valid @RequestBody UserRegisterParam userRegisterParam) {
        System.out.println("用户注册：userRegisterParam:" + JSONUtil.toJsonStr(userRegisterParam));
        AppConnect appConnect = null;
        if (StrUtil.isNotBlank(userRegisterParam.getTempUid())) {
            appConnect = appConnectService.getByTempUid(userRegisterParam.getTempUid());
            System.out.println("用户注册：appConnect:" + JSONUtil.toJsonStr(appConnect));
            // authSocial.getUid() != null 这个是说明已经绑定好用户了，不知道为什么没判断出来
            if (appConnect == null || appConnect.getUserId() != null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            if (Objects.equals(appConnect.getAppId(), SocialType.MA.value())) {
                // 小程序注册
                WxMaPhoneNumberInfo phoneNumber = getPhoneNumber(appConnect.getBizTempSession(), userRegisterParam.getEncryptedData(), userRegisterParam.getIvStr());
                log.info("小程序注册phoneNumber:{}", Json.toJsonString(phoneNumber));
                userRegisterParam.setMobile(phoneNumber.getPhoneNumber());
                if (StrUtil.isBlank(userRegisterParam.getNickName())) {
                    userRegisterParam.setNickName(phoneNumber.getPhoneNumber());
                }
                userRegisterParam.setUserName(phoneNumber.getPhoneNumber());
                userRegisterParam.setImg(appConnect.getImageUrl());
            } else if (Objects.equals(appConnect.getAppId(), SocialType.MP.value())) {
                // 公众号注册
                userRegisterParam.setNickName(appConnect.getNickName());
                userRegisterParam.setImg(appConnect.getImageUrl());
            }
        } else {
            //普通账户注册
            // 如果有用户名,就判断用户名格式是否正确
            if (!PrincipalUtil.isUserName(userRegisterParam.getUserName())) {
                // 用户名应由4-16位数字字母下划线组成
                throw new AppBindException("用户名应由4-16位数字字母下划线组成");
            }
            if (StrUtil.isBlank(userRegisterParam.getNickName())) {
                userRegisterParam.setNickName(userRegisterParam.getUserName());
            }
            // 目前注册都是发验证码去注册的，看看有没有校验验证码成功的标识
            userService.validate(userRegisterParam, CHECK_REGISTER_SMS_FLAG + userRegisterParam.getCheckRegisterSmsFlag());
        }
        // 正在进行申请注册
        if (userService.count(new LambdaQueryWrapper<User>().eq(User::getUserMobile, userRegisterParam.getMobile())) > 0) {
            if (Objects.equals(appConnect.getAppId(), SocialType.MA.value())) {
                //TODO 账号绑定微信号
            }
            // 该手机号已注册，无法重新注册
            throw new AppBindException("该手机号已注册，无法重新注册");
        }

        // 新建用户
        User user = new User();
        user.setModifyTime(new Date());
        user.setUserRegtime(new Date());
        user.setUserRegip(IPHelper.getIpAddr());
        user.setStatus(1);
        user.setPic(userRegisterParam.getImg());
        user.setUserMobile(userRegisterParam.getMobile());
        user.setUserName(userRegisterParam.getUserName());
        if (StrUtil.isNotBlank(userRegisterParam.getPassword())) {
            user.setLoginPassword(passwordEncoder.encode(userRegisterParam.getPassword()));
        }
        user.setNickName(userRegisterParam.getNickName());

        appConnectService.registerOrBindUser(user, appConnect);
        // 2. 登录
        UserInfoInTokenBO userInfoInTokenBO = new UserInfoInTokenBO();
        userInfoInTokenBO.setUserId(user.getUserId());
        userInfoInTokenBO.setSysType(SysTypeEnum.ORDINARY.value());
        userInfoInTokenBO.setIsAdmin(0);
        userInfoInTokenBO.setEnabled(true);

        if (appConnect != null) {
            userInfoInTokenBO.setBizUserId(appConnect.getBizUserId());
            userInfoInTokenBO.setBizUid(appConnect.getBizUnionid());
            userInfoInTokenBO.setSocialType(appConnect.getAppId());
            userInfoInTokenBO.setSessionKey(appConnect.getBizTempSession());
        }

        return ResponseEntity.ok(tokenStore.storeAndGetVo(userInfoInTokenBO));
    }


    @PutMapping("/sendRegisterSms")
    @ApiOperation(value = "发送注册验证码", notes = "发送注册验证码")
    public ResponseEntity<Void> register(@Valid @RequestBody SendSmsParam sendSmsParam) {
//        if (userService.count(new LambdaQueryWrapper<User>().eq(User::getUserMobile, sendSmsParam.getMobile())) > 0) {
//            // 该手机号已注册，无法重新注册
//            throw new YamiShopBindException("该手机号已注册，无法重新注册");
//        }
        // 每个手机号每分钟只能发十个注册的验证码，免得接口被利用
        smsLogService.sendSms(SendType.REGISTER, sendSmsParam.getMobile(), sendSmsParam.getMobile(), Maps.newHashMap());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/checkRegisterSms")
    @ApiOperation(value = "校验验证码", notes = "校验验证码返回校验成功的标识")
    public ResponseEntity<String> register(@Valid @RequestBody CheckRegisterSmsParam checkRegisterSmsParam) {
        // 每个ip每分钟只能发十个注册的验证码，免得接口被利用
        if (!smsLogService.checkValidCode(checkRegisterSmsParam.getMobile(), checkRegisterSmsParam.getValidCode(), SendType.REGISTER)) {
            // 验证码有误或已过期
            throw new AppBindException("验证码有误或已过期");
        }
        String checkRegisterSmsFlag = IdUtil.simpleUUID();
        RedisUtil.set(CHECK_REGISTER_SMS_FLAG + checkRegisterSmsFlag, checkRegisterSmsParam.getMobile(), 600);
        return ResponseEntity.ok(checkRegisterSmsFlag);
    }

    @PutMapping("/sendBindSms")
    @ApiOperation(value = "发送绑定验证码", notes = "发送绑定验证码")
    public ResponseEntity<Void> bindSms(@Valid @RequestBody SendSmsParam sendSmsParam) {
        // 每个手机号每分钟只能发十个注册的验证码，免得接口被利用
        smsLogService.sendSms(SendType.VALID, sendSmsParam.getMobile(), sendSmsParam.getMobile(), Maps.newHashMap());
        return ResponseEntity.ok().build();
    }


    @PutMapping("/checkUpdatePwdSms")
    @ApiOperation(value = "修改密码校验验证码", notes = "校验验证码返回校验成功的标识")
    public ResponseEntity<String> checkUpdatePwdSms(@RequestBody CheckRegisterSmsParam checkRegisterSmsParam) {
        boolean isCheckPass = false;
        if (Objects.nonNull(checkRegisterSmsParam) && Objects.nonNull(checkRegisterSmsParam.getMobile())) {
            Matcher m = Pattern.compile(PrincipalUtil.MOBILE_REGEXP).matcher(checkRegisterSmsParam.getMobile());
            isCheckPass = m.matches();
        }
        if (!isCheckPass) {
            throw new AppBindException("请输入正确手机号！");
        }

        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserMobile, checkRegisterSmsParam.getMobile()));
        if (user == null) {
            // 此用户不存在，请先注册
            throw new AppBindException("此用户不存在，请先注册");
        }
        if (!smsLogService.checkValidCode(user.getUserMobile(), checkRegisterSmsParam.getValidCode(), SendType.UPDATE_PASSWORD)) {
            // 验证码有误或已过期
            throw new AppBindException("验证码有误或已过期");
        }
        String checkRegisterSmsFlag = IdUtil.simpleUUID();
        RedisUtil.set(CHECK_UPDATE_PWD_SMS_FLAG + checkRegisterSmsFlag, checkRegisterSmsParam.getMobile(), 600);
        return ResponseEntity.ok(checkRegisterSmsFlag);
    }

    @PutMapping("/updatePwd")
    @ApiOperation(value = "修改密码", notes = "修改密码")
    public ResponseEntity<Void> updatePwd(@Valid @RequestBody UserPwdUpdateParam userPwdUpdateParam) {
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserMobile, userPwdUpdateParam.getMobile()));
        if (user == null) {
            // 无法获取用户信息
            throw new AppBindException("无法获取用户信息");
        }
        UserRegisterParam registerParam = mapperFacade.map(userPwdUpdateParam, UserRegisterParam.class);
        // 看看有没有校验验证码成功的标识
        userService.validate(registerParam, CHECK_UPDATE_PWD_SMS_FLAG + userPwdUpdateParam.getCheckRegisterSmsFlag());
        if (StrUtil.isBlank(userPwdUpdateParam.getPassword())) {
            // 新密码不能为空
            throw new AppBindException("新密码不能为空");
        }
        if (StrUtil.equals(passwordEncoder.encode(userPwdUpdateParam.getPassword()), user.getLoginPassword())) {
            // 新密码不能与原密码相同!
            throw new AppBindException("新密码不能与原密码相同!");
        }
        user.setModifyTime(new Date());
        user.setLoginPassword(passwordEncoder.encode(userPwdUpdateParam.getPassword()));
        userService.updateById(user);
        tokenStore.deleteAllToken(SysTypeEnum.ORDINARY.value().toString(), user.getUserId());
        return ResponseEntity.ok().build();
    }

    /**
     * 获取用户绑定手机号
     *
     * @param encryptedData
     * @param ivStr
     * @return
     */
    private WxMaPhoneNumberInfo getPhoneNumber(String sessionKey, String encryptedData, String ivStr) {

        return wxConfig.getWxMaService().getUserService().getPhoneNoInfo(sessionKey, encryptedData, ivStr);
    }


    @ApiOperation(value = "手机号注册或登录", notes = "手机号注册或登录,为查报告的写的，精简版注册")
    @PostMapping("/phoneRegister")
    public ResponseEntity phoneRegister(@RequestBody CheckRegisterSmsParam checkRegisterSmsParam) {
        // 每个ip每分钟只能发十个注册的验证码，免得接口被利用
        if (!smsLogService.checkValidCode(checkRegisterSmsParam.getMobile(), checkRegisterSmsParam.getValidCode(), SendType.REGISTER)) {
            // 验证码有误或已过期
            throw new AppBindException("验证码有误或已过期");
        }

        //有用户就登录,没有就注册
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserMobile, checkRegisterSmsParam.getMobile()));
        if (ObjectUtils.isEmpty(user)) {
            // 新建用户
            user = new User();
            user.setModifyTime(new Date());
            user.setUserRegtime(new Date());
            user.setUserRegip(IPHelper.getIpAddr());
            user.setStatus(1);
            user.setUserMobile(checkRegisterSmsParam.getMobile());
            user.setUserName(checkRegisterSmsParam.getMobile());
            user.setNickName(checkRegisterSmsParam.getMobile());
            appConnectService.registerOrBindUser(user, null);
        }
        // 2. 登录
        UserInfoInTokenBO userInfoInTokenBO = new UserInfoInTokenBO();
        userInfoInTokenBO.setUserId(user.getUserId());
        userInfoInTokenBO.setSysType(SysTypeEnum.ORDINARY.value());
        userInfoInTokenBO.setIsAdmin(0);
        userInfoInTokenBO.setEnabled(true);

        return ResponseEntity.ok(tokenStore.storeAndGetVo(userInfoInTokenBO));
    }



    @ApiOperation(value = "手机号注册或登录跳过检查", notes = "手机号注册或登录跳过检查,测试完删掉")
    @PostMapping("/phoneRegisterSkipCheck")
    public ResponseEntity phoneRegisterSkipCheck(@RequestBody CheckRegisterSmsParam checkRegisterSmsParam) {
        // 每个ip每分钟只能发十个注册的验证码，免得接口被利用
        if (!smsLogService.checkValidCode(checkRegisterSmsParam.getMobile(), checkRegisterSmsParam.getValidCode(), SendType.REGISTER)) {
            // 验证码有误或已过期
            throw new AppBindException("验证码有误或已过期");
        }

        //有用户就登录,没有就注册
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserMobile, checkRegisterSmsParam.getMobile()));
        if (ObjectUtils.isEmpty(user)) {
            // 新建用户
            user = new User();
            user.setModifyTime(new Date());
            user.setUserRegtime(new Date());
            user.setUserRegip(IPHelper.getIpAddr());
            user.setStatus(1);
            user.setUserMobile(checkRegisterSmsParam.getMobile());
            user.setUserName(checkRegisterSmsParam.getMobile());
            user.setNickName(checkRegisterSmsParam.getMobile());
            appConnectService.registerOrBindUser(user, null);
        }

        // 2. 登录
        UserInfoInTokenBO userInfoInTokenBO = new UserInfoInTokenBO();
        userInfoInTokenBO.setUserId(user.getUserId());
        userInfoInTokenBO.setSysType(SysTypeEnum.ORDINARY.value());
        userInfoInTokenBO.setIsAdmin(0);
        userInfoInTokenBO.setEnabled(true);

        //第三方用户
        String openId = checkRegisterSmsParam.getOpenId();
        if (StringUtils.isNotEmpty(openId)){
            Integer socialType = SocialType.MA.value();
            AppConnect appConnect = appConnectService.getByBizUserId(openId, socialType);
            //手机号登录 可能微信会跟之前的不一样，每次都存最新的openid
            if (ObjectUtils.isEmpty(appConnect)) {
                String tempUid = IdUtil.simpleUUID();
                appConnect = new AppConnect();
                appConnect.setAppId(socialType);
                appConnect.setTempUid(tempUid);
                appConnect.setBizUserId(openId);
                appConnect.setUserId(user.getUserId());
                appConnectService.save(appConnect);
            }else {
                appConnect.setBizUserId(openId);
                appConnectService.updateById(appConnect);
            }
            userInfoInTokenBO.setBizUserId(appConnect.getBizUserId());
        }

        return ResponseEntity.ok(tokenStore.storeAndGetVo(userInfoInTokenBO));
    }
}
