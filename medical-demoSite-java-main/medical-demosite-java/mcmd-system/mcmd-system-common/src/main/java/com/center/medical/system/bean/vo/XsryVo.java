package com.center.medical.system.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 客户转移返回参数
 */
@Data
public class XsryVo implements Serializable {
    private static final long serialVersionUID = 1321701957996564869L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "是否领导")
    private String isleader;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "昵称")
    private String nickName;
}
