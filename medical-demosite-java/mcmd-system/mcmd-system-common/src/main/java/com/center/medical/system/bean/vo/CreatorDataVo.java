package com.center.medical.system.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 银行汇款结算 获取创建人 返回数据
 */
@Data
public class CreatorDataVo implements Serializable {
    private static final long serialVersionUID = -8159098891415394950L;

    @ApiModelProperty(value = "用户编号")
    private String userID;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "分中心")
    private String fzx;

}
