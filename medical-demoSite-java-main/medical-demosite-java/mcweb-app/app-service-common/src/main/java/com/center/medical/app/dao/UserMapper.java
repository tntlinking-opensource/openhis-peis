/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.app.bean.model.User;
import com.center.medical.app.common.util.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author yami
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 通过第三方用户id获取对应客户端用户
     *
     * @param appId
     * @param bizUserId
     * @return
     */
    User getUserByBizUserId(@Param("appId") Integer appId, @Param("bizUserId") String bizUserId);

    /**
     * 获取用户详情
     *
     * @param userId 用户id
     * @return 用户
     */
    User getUserDetail(@Param("userId") String userId);

    /**
     * 条件按查询，分页获取用户列表
     *
     * @param page 分页参数
     * @param user 查询条件
     * @return 分页用户列表
     */
    IPage<User> getUserPage(PageParam<User> page, @Param("user") User user);

    /**
     * 获取用户id和用户注册时间
     *
     * @param userSet 用户列表
     * @return 用户列表
     */
    List<User> getUserListByUserNumbers(@Param("userSet") Set<String> userSet);

    /**
     * 根据多个手机号获取对应的用户
     *
     * @param phones 手机号集合
     * @return 用户列表
     */
    List<User> listByUserPhones(@Param("phones") List<String> phones);

    /**
     * 根据多个邮箱获取对应的用户
     *
     * @param mails 邮箱集合
     * @return 用户列表
     */
    List<User> listByUserMails(@Param("mails") List<String> mails);

    /**
     * 根据用户名查询用户信息(区分用户名大小写)
     *
     * @param userName
     * @return
     */
    User selectOneByUserName(@Param("userName") String userName);

    /**
     * 根据用户名统计用户数据
     *
     * @param userName
     * @return
     */
    Integer selectCountByUserName(@Param("userName") String userName);

    /**
     * 获取指定用户名
     *
     * @param nickNames
     * @return
     */
    List<String> listUserName(@Param("nickNames") Set<String> nickNames);

}
