/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.security.common.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.app.bean.model.User;
import com.center.medical.app.bean.param.UserRegisterExcelParam;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.dao.UserMapper;
import com.center.medical.app.security.common.dao.AppConnectMapper;
import com.center.medical.app.security.common.model.AppConnect;
import com.center.medical.app.security.common.service.AppConnectService;
import com.center.medical.app.service.UserService;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author lgh on 2018/09/07.
 */
@Service
@AllArgsConstructor
public class AppConnectServiceImpl extends ServiceImpl<AppConnectMapper, AppConnect> implements AppConnectService {

    private final AppConnectMapper appConnectMapper;
    private final ApplicationContext applicationContext;

    private final UserMapper userMapper;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final MapperFacade mapperFacade;

    /**
     * YamiUserServiceImpl#insertUserIfNecessary 将会清除该缓存信息
     *
     * @param bizUserId
     * @return
     */
    @Override
    public AppConnect getByBizUserId(String bizUserId, Integer appId) {
        return appConnectMapper.getByBizUserId(bizUserId, appId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User registerOrBindUser(User user, AppConnect appConnect) {
        if (userMapper.selectCountByUserName(user.getUserName()) > 0) {
            // 用户名已存在
            throw new AppBindException("用户名已存在");
        }
        String userId = IdUtil.simpleUUID();
        user.setUserId(userId);
        user.setLevel(1L);
        user.setLevelType(0);
        userMapper.insert(user);
        if (appConnect != null) {
            appConnectMapper.bindUserIdByTempUid(user.getUserId(), appConnect.getTempUid());
        }
        return user;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer batchRegisterUser(List<UserRegisterExcelParam> userList, Set<String> repeatPhoneSet, Set<String> repeatMailSet) {
        // 成功注册用户条数
        int registerNum = 0;
        if (CollectionUtils.isEmpty(userList)) {
            return registerNum;
        }
        List<String> phones = userList.stream().map(UserRegisterExcelParam::getPhone).collect(Collectors.toList());
        List<String> mails = userList.stream().map(UserRegisterExcelParam::getUserMail).collect(Collectors.toList());
        // 查询已经存在的手机号用户
        List<User> users = userMapper.listByUserPhones(phones);
        // 查询已经存在的邮箱用户
        List<User> userListByMail = userMapper.listByUserMails(mails);
        if (CollectionUtils.isNotEmpty(users)) {
            List<String> mobiles = users.stream().map(User::getUserMobile).collect(Collectors.toList());
            userList = userList.stream().filter(item -> !mobiles.contains(item.getPhone())).collect(Collectors.toList());
            repeatPhoneSet.addAll(mobiles);
        }
        if (CollectionUtils.isNotEmpty(userListByMail) && CollectionUtils.isNotEmpty(userList)) {
            List<String> userMails = userListByMail.stream().map(User::getUserMail).collect(Collectors.toList());
            userList = userList.stream().filter(item -> !userMails.contains(item.getUserMail())).collect(Collectors.toList());
            repeatMailSet.addAll(userMails);
        }
        if (CollectionUtils.isEmpty(userList)) {
            return registerNum;
        }
        // 获取用户名
        Set<String> nickNameSet = userList.stream().filter(user -> StrUtil.isNotBlank(user.getNickName())).map(UserRegisterExcelParam::getNickName).collect(Collectors.toSet());
        List<String> userNameList = new ArrayList<>();
        if (CollUtil.isNotEmpty(nickNameSet)) {
            userNameList = userMapper.listUserName(nickNameSet);
        }
        registerNum = userList.size();
        Date now = new Date();
        // 用户
        List<User> userSave = new ArrayList<>();
        for (UserRegisterExcelParam param : userList) {
            // 用户
            String userId = IdUtil.simpleUUID();
            User user = mapperFacade.map(param, User.class);
            user.setUserId(userId);
            user.setUserMobile(param.getPhone());
            user.setLoginPassword(passwordEncoder.encode(param.getPassword()));
            if (StrUtil.isNotBlank(param.getNickName()) && !userNameList.contains(param.getNickName())) {
                user.setUserName(param.getNickName());
                userNameList.add(param.getNickName());
            } else {
                user.setUserName(param.getPhone());
            }
            user.setModifyTime(now);
            user.setUserRegtime(now);
            user.setStatus(1);
            userSave.add(user);
        }
        userService.saveBatch(userSave);
        return registerNum;
    }

    @Override
    public AppConnect getByTempUid(String tempUid) {
        return appConnectMapper.getByTempUid(tempUid);
    }

}
