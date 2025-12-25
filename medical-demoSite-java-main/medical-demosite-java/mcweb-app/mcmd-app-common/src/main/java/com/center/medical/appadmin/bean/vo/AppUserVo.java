package com.center.medical.appadmin.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 小程序用户返回数据
 */
@Data
public class AppUserVo implements Serializable {
    private static final long serialVersionUID = 6623796589924034644L;

    @ApiModelProperty(value = "ID")
    private String userId;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户手机号")
    private String userMobile;

    @ApiModelProperty(value = "头像图片路径")
    private String pic;

    @ApiModelProperty(value = "注册时间")
    private Date userRegtime;

    @ApiModelProperty(value = "体检次数")
    private String number;

    @ApiModelProperty(value = "最后一次体检时间")
    private String lastTime;
}
