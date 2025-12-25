package com.center.medical.finance.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 卡发放分页参数
 */
@Data
public class SendCardParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -1470446548603165524L;

    @ApiModelProperty(value = "卡号")
    private String cardNo;

    @ApiModelProperty(value = "卡类型ID")
    private String typeId;

    @ApiModelProperty(value = "卡类型ID")
    private String type;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "卡类型")
    private String card;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户编号")
    private String userNo;
}
