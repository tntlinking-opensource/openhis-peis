/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 */

package com.center.medical.app.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息记录表
 *
 * @author lhd
 * @date 2020-07-01 15:44:27
 */
@Data
@TableName("md_notify_log")
public class NotifyLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "log_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "消息记录表Id")
    private Long logId;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "通知的用户id")
    private String remindId;

    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    @TableField(exist = false)
    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    @ApiModelProperty(value = "通知类型 1.短信发送 2.公众号订阅消息 3.站内消息")
    private Integer remindType;

    @ApiModelProperty(value = "通知短信类型")
    private Integer sendType;

    @ApiModelProperty(value = "用户手机号")
    private String userMobile;

    @ApiModelProperty(value = "通知模板id")
    private Long templateId;

    @ApiModelProperty(value = "通知内容")
    private String message;

    @ApiModelProperty(value = "是否阅读 1已读 0未读")
    private Integer status;

    @ApiModelProperty(value = "记录时间")
    private Date createTime;

    @ApiModelProperty(value = "订单号")
    private String orderNumber;

    @ApiModelProperty(value = "消息相关参数内容")
    private String paramContent;

    @ApiModelProperty("短信模板code")
    @TableField(exist = false)
    private String templateCode;

    @ApiModelProperty("公众号模板code")
    @TableField(exist = false)
    private String mpCode;
}
