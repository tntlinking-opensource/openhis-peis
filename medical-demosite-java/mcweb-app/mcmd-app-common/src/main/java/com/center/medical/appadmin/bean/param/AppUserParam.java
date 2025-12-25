package com.center.medical.appadmin.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 小程序用户查询参数
 */
@Data
public class AppUserParam implements Serializable {
    private static final long serialVersionUID = 9059001302102430153L;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户手机号")
    private String userMobile;


}
