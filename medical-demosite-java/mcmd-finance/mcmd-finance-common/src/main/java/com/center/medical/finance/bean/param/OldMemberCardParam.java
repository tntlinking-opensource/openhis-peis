package com.center.medical.finance.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取老系统会员卡数据参数
 */
@Data
public class OldMemberCardParam implements Serializable {
    private static final long serialVersionUID = 7514587353080618752L;

    @ApiModelProperty(value = "授权码")
    private String authCode;

    @ApiModelProperty(value = "卡号")
    private String cardNo;

    @ApiModelProperty(value = "电话号")
    private String phone;

}
