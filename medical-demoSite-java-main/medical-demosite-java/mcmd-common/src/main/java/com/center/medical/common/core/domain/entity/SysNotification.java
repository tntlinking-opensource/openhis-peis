package com.center.medical.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 通知配置表 sys_notice_config
 *
 */
@Data
@TableName("sys_notification")
@ApiModel(value = "通知记录表", description = "通知记录表")
public class SysNotification extends Model<SysNotification> implements Serializable {

    private static final long serialVersionUID = -952752700044563720L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "通知用户id")
    private String tarUserId;

    @ApiModelProperty(value = "消息类型")
    private String noticeConfigId;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "是否已读")
    private Integer isRead;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

}
