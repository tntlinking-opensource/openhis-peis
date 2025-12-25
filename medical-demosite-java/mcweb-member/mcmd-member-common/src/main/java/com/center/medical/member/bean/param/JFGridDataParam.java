package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 积分充值GridData参数
 */
@Data
public class JFGridDataParam implements Serializable {
    private static final long serialVersionUID = -5531047724722883346L;

    @ApiModelProperty(value = "卡号")
    private String cardno;

    @ApiModelProperty(value = "金钱，消费传正数")
    private Double money;

    @ApiModelProperty(value = "备注")
    private String memotext;

}
