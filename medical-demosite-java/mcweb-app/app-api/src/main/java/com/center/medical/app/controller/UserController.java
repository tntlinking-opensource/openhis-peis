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


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.app.bean.dto.UserDto;
import com.center.medical.app.bean.model.FamilyList;
import com.center.medical.app.bean.model.User;
import com.center.medical.app.bean.param.UserInfoParam;
import com.center.medical.app.security.util.SecurityUtils;
import com.center.medical.app.service.FamilyListService;
import com.center.medical.app.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 用户信息
 *
 * @author LGH
 */
@RestController
@RequestMapping("/api/v1/user")
@Api(tags = "用户接口")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final MapperFacade mapperFacade;
    private final FamilyListService familyListService;

    @GetMapping("/userInfo")
    @ApiOperation(value = "查看用户信息", notes = "获取当前登录用户的用户信息")
    public ResponseEntity<UserDto> userInfo() {
        String userId = SecurityUtils.getUser().getUserId();
        User user = userService.getUserByUserId(userId);
        if (Objects.isNull(user)) {
            return ResponseEntity.ok().build();
        }
        UserDto userDto = mapperFacade.map(user, UserDto.class);
        userDto.setUsername(user.getUserName());
        userDto.setLevelType(user.getLevelType());
        if (userDto.getUserMobile() != null) {
            userDto.setMobile(userDto.getUserMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        }
        //家庭列表人数
        long count = familyListService.count(new LambdaQueryWrapper<FamilyList>().eq(FamilyList::getUserId, userId));
        userDto.setFamilyListSize(count);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/setUserInfo")
    @ApiOperation(value = "设置用户信息", notes = "设置用户信息")
    public ResponseEntity<Void> setUserInfo(@RequestBody UserInfoParam userInfoParam) {
        String userId = SecurityUtils.getUser().getUserId();
        User user = new User();
        user.setUserId(userId);
        user.setPic(StrUtil.isBlank(userInfoParam.getAvatarUrl()) ? user.getPic() : userInfoParam.getAvatarUrl());
        user.setSex(userInfoParam.getSex() == null ? user.getSex() : userInfoParam.getSex());
        user.setUserMobile(userInfoParam.getUserMobile() == null ? user.getUserMobile() : userInfoParam.getUserMobile());
        user.setBirthDate(userInfoParam.getBirthDate() == null ? user.getBirthDate() : userInfoParam.getBirthDate());
        user.setUserMail(StrUtil.isNotBlank(userInfoParam.getUserMail()) ? userInfoParam.getUserMail() : user.getUserMail());
        if (StrUtil.isNotBlank(userInfoParam.getNickName())) {
            user.setNickName(userInfoParam.getNickName());
        }
        userService.updateById(user);
        return ResponseEntity.ok().build();
    }
}
