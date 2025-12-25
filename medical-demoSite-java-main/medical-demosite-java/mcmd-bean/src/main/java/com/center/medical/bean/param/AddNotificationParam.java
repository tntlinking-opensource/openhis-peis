package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 添加通知
 */
@Data
public class AddNotificationParam implements Serializable {
    private static final long serialVersionUID = 7507269673572890600L;

    @ApiModelProperty(value = "通知用户id")
    private String tarUserId;

    @ApiModelProperty(value = "消息类型")
    private String noticeConfigId;

    @ApiModelProperty(value = "替换的占位符")
    private String placeholder;


    public AddNotificationParam(String tarUserId, String noticeConfigId, String placeholder) {
        this.tarUserId = tarUserId;
        this.noticeConfigId = noticeConfigId;
        this.placeholder = placeholder;
    }


    public AddNotificationParam() {
    }
}
