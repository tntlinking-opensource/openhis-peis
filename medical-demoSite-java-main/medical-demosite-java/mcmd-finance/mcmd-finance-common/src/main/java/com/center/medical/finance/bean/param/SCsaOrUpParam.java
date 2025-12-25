package com.center.medical.finance.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 卡发放保存参数
 */
@Data
public class SCsaOrUpParam implements Serializable {
    private static final long serialVersionUID = 7496520116945938075L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "卡类型ID")
    private String typeId;

    @ApiModelProperty(value = "卡前缀")
    private String cardPrefix;

    @ApiModelProperty(value = "卡标识")
    private String cardLogo;

    @ApiModelProperty(value = "有效期")
    private Date validity;

    @ApiModelProperty(value = "卡内剩余的金额(会员卡、体检卡)")
    private Double balanceLimit;

    @ApiModelProperty(value = "zdkh")
    private String zdkh;

    @ApiModelProperty(value = "启始卡号")
    private String qskh;

    @ApiModelProperty(value = "数量")
    private Integer sl;

    @ApiModelProperty(value = "收费员ID(弃用)")
    private String getterId;

    @ApiModelProperty(value = "领取时间")
    private Date getTime;

    @ApiModelProperty(value = "jctcId")
    private Date jctcId;

    @ApiModelProperty(value = "订单ID（团检专属卡）")
    private String orderId;

    @ApiModelProperty(value = "套餐id（团检专属卡)||十周年  最小套餐id")
    private String tcId;

    @ApiModelProperty(value = "生成密码，true是false否")
    private String generatePassword;

    @ApiModelProperty(value = "卡说明")
    private String cardState;

    @ApiModelProperty(value = "回访备注")
    private String memo;

    @ApiModelProperty(value = "会员卡剩余积分（会员卡和家庭卡使用）")
    private Double balanceJf;

    @ApiModelProperty(value = "剩余金额(只有家庭卡使用这个字段)")
    private Double balanceMoney;
}
