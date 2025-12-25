/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.security.common.model;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author yami
 */
@Data
@TableName("md_app_connect")
public class AppConnect {
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 本系统userId
     */
    private String userId;

    /**
     * 第三方系统id 1：微信小程序
     */
    private Integer appId;

    /**
     * 第三方系统昵称
     */
    private String nickName;

    /**
     * 第三方系统头像
     */
    private String imageUrl;

    /**
     * 第三方系统userid
     */
    private String bizUserId;

    /**
     * 第三方系统unionid
     */
    private String bizUnionid;

    private String tempUid;

    private String bizTempSession;

}
