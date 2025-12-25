package com.center.medical.finance.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 卡类型改变后获取卡前缀等字段
 */
@Data
public class ChangeDataVo implements Serializable {
    private static final long serialVersionUID = -4087425624715690419L;

    @ApiModelProperty(value = "卡前缀")
    private String cardPrefix;

    @ApiModelProperty(value = "卡标识")
    private String cardLogo;

    @ApiModelProperty(value = "卡内剩余的金额(会员卡、体检卡)")
    private Double balanceLimit;

    @ApiModelProperty(value = "卡说明")
    private String cardState;

    @ApiModelProperty(value = "zdkh")
    private String zdkh;

    @ApiModelProperty(value = "启始卡号")
    private String qskh;

    @ApiModelProperty(value = "类型")
    private Integer type;
}
