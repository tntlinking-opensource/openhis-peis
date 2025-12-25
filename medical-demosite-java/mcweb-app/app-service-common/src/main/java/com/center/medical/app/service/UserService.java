/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.app.bean.model.User;
import com.center.medical.app.bean.param.UserRegisterParam;
import com.center.medical.app.common.util.PageParam;

import java.util.Map;
import java.util.Set;

/**
 * @author lgh on 2018/09/11.
 */
public interface UserService extends IService<User> {

    /**
     * 通过用户id获取用户信息
     *
     * @param userId 用户id
     * @return 用户详情
     */
    User getUserByUserId(String userId);

    /**
     * 根据用户id获取用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    User getUserDetail(String userId);

    /**
     * 校验用户注册验证码
     *
     * @param userRegisterParam    用户注册参数
     * @param checkRegisterSmsFlag 用户注册发送短信缓存的key
     */
    void validate(UserRegisterParam userRegisterParam, String checkRegisterSmsFlag);

    /**
     * 条件查询，分页获取用户信息
     *
     * @param page 分页参数
     * @param user 条件查询参数
     * @return 分页用户信息列表
     */
    IPage<User> getUserPage(PageParam<User> page, User user);

    /**
     * 获取用户信息map
     *
     * @param keySet 用户id集合
     * @return 用户的map信息
     */
    Map<String, User> getUserListByUserNumbers(Set<String> keySet);
}
