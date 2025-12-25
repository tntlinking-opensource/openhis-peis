/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.app.bean.model.User;
import com.center.medical.app.bean.param.UserRegisterParam;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.common.util.PageParam;
import com.center.medical.app.common.util.RedisUtil;
import com.center.medical.app.dao.UserMapper;
import com.center.medical.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lgh on 2018/09/11.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public User getUserByUserId(String userId) {
        return userMapper.selectById(userId);
    }

    /**
     * 看看有没有校验验证码成功的标识
     *
     * @param userRegisterParam
     * @param checkRegisterSmsFlag
     */
    @Override
    public void validate(UserRegisterParam userRegisterParam, String checkRegisterSmsFlag) {
        if (StrUtil.isBlank(userRegisterParam.getCheckRegisterSmsFlag())) {
            // 验证码已过期，请重新发送验证码校验
            throw new AppBindException("验证码已过期，请重新发送验证码校验");
        } else {
            String checkRegisterSmsFlagMobile = RedisUtil.get(checkRegisterSmsFlag);
            if (!Objects.equals(checkRegisterSmsFlagMobile, userRegisterParam.getMobile())) {
                // 验证码已过期，请重新发送验证码校验
                throw new AppBindException("验证码已过期，请重新发送验证码校验");
            }
        }
    }

    @Override
    public User getUserDetail(String userId) {
        return userMapper.getUserDetail(userId);
    }

    @Override
    public IPage<User> getUserPage(PageParam<User> page, User user) {
        return userMapper.getUserPage(page, user);
    }

    @Override
    public Map<String, User> getUserListByUserNumbers(Set<String> keySet) {
        if (keySet.size() == 0) {
            return new HashMap<>(16);
        }
        List<User> userList = userMapper.getUserListByUserNumbers(keySet);
        Map<String, User> userMap = userList.stream().collect(Collectors.toMap(User::getUserId, user -> user));
        return userMap;
    }

}
