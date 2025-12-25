package com.center.medical.system.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通知记录表 分页参数
 */
@Data
public class SysNotificationPageParam implements Serializable {
    private static final long serialVersionUID = 506610290366706942L;

    @ApiModelProperty(value = "通知用户id(不用传)")
    private String tarUserId;


    @ApiModelProperty(value = "消息类型")
    private String noticeConfigId;


    @ApiModelProperty(value = "是否已读")
    private Integer isRead;

}
