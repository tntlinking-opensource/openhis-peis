package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ApproveTjtcDataParam implements Serializable {
    private static final long serialVersionUID = -2494058447345868325L;


    @ApiModelProperty(value = "审核订单id,用于展示包含的套餐")
    private String apprddId;

    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "体检套餐输入码")
    private String tjtcsrm;

    @ApiModelProperty(value = "体检类型")
    private String tjlx;

    @ApiModelProperty(value = "适用性别")
    private String syxb;

    @ApiModelProperty(value = "套餐状态")
    private String apprtcstate;

    @ApiModelProperty(value = "折后价格")
    private Double zhjg;

}
