/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.security.common.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.app.bean.model.User;
import com.center.medical.app.bean.param.UserRegisterExcelParam;
import com.center.medical.app.security.common.model.AppConnect;

import java.util.List;
import java.util.Set;

/**
 * @author lgh on 2018/09/07.
 */
public interface AppConnectService extends IService<AppConnect> {

    /**
     * 根据第三方userId和第三方系统id获取用户关联第三方信息
     *
     * @param bizUserId 第三方userId
     * @param appId     第三方系统id
     * @return 用户关联第三方信息
     */
    AppConnect getByBizUserId(String bizUserId, Integer appId);

    /**
     * 用户注册或者绑定第三方账号信息
     *
     * @param user       用户信息
     * @param appConnect 第三方账号信息
     * @return
     */
    User registerOrBindUser(User user, AppConnect appConnect);

    /**
     * 批量注册用户
     *
     * @param userList       用户列表
     * @param repeatPhoneSet 重复手机号信息
     * @param repeatMailSet  重复邮箱信息
     * @return 结果
     */
    Integer batchRegisterUser(List<UserRegisterExcelParam> userList, Set<String> repeatPhoneSet, Set<String> repeatMailSet);

    /**
     * 获取根据尝试社交登录时，保存的临时的uid获取社交信息
     *
     * @param tempUid tempUid
     * @return 用户社交账号信息
     */
    AppConnect getByTempUid(String tempUid);
}
