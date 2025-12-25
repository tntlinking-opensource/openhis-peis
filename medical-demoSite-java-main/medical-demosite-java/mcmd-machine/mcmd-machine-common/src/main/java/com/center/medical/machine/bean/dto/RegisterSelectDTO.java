package com.center.medical.machine.bean.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RegisterSelectDTO implements Serializable {

    @ApiModelProperty(value = "体检号")
    private String patientCode;

    @ApiModelProperty(value = "姓名")
    private String patientName;

    @ApiModelProperty(value = "团队名称")
    private String teamName;

    @ApiModelProperty(value = "套餐名称")
    private String mealName;

    @ApiModelProperty(value = "创建时间")
    private String createDate;

    @ApiModelProperty(value = "限度")
    private BigDecimal tsLimit;

    @ApiModelProperty(value = "支付金额")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "注册")
    private Integer canReg;

    @ApiModelProperty(value = "是否支付")
    private Integer isPay;

    @ApiModelProperty(value = "是否禁检")
    private Integer fPaused;

}
