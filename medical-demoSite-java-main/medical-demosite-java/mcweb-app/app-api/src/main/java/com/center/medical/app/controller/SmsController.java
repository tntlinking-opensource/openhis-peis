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

import com.center.medical.app.bean.enums.SendType;
import com.center.medical.app.bean.param.SendSmsParam;
import com.center.medical.app.service.SmsLogService;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LGH
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sms")
@Api(tags = "发送验证码接口")
public class SmsController {

    private final SmsLogService smsLogService;

    @PostMapping("/sendLoginCode")
    @ApiOperation(value = "发送登录验证码", notes = "用户发送登录验证码")
    public ResponseEntity<Void> sendLoginCode(@RequestBody SendSmsParam sendSmsParam) {
        // 每个手机号每分钟只能发十个注册的验证码，免得接口被利用
        smsLogService.sendSms(SendType.LOGIN, sendSmsParam.getMobile(), sendSmsParam.getMobile(), Maps.newHashMap());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sendUpdatePwdCode")
    @ApiOperation(value = "发送修改密码验证码接口", notes = "发送修改密码验证码接口")
    public ResponseEntity<Void> sendUpdatePwdCode(@RequestBody SendSmsParam sendSmsParam) {
        // 每个手机号每分钟只能发十个注册的验证码，免得接口被利用
        smsLogService.sendSms(SendType.UPDATE_PASSWORD, sendSmsParam.getMobile(), sendSmsParam.getMobile(), Maps.newHashMap());
        return ResponseEntity.ok().build();
    }
}
