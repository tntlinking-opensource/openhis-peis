package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 线上备单发送短信 查询数据
 */
@Data
public class NeedNoticeVo implements Serializable {
    private static final long serialVersionUID = -2897594995102164571L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "业务编号")
    private String patientbizno;

    @ApiModelProperty(value = "微信沃德小程序体检码")
    private String wechatCode;

    @ApiModelProperty(value = "微信沃德小程序是否已通知")
    private Integer isWechatNoticed;

    @ApiModelProperty(value = "微信沃德小程序通知类型  1短信 2微信")
    private String wechatNoticeType;

    @ApiModelProperty(value = "微信沃德小程序通知时间")
    private Date wechatNoticeTime;

    @ApiModelProperty(value = "电话")
    private String phone;
}
