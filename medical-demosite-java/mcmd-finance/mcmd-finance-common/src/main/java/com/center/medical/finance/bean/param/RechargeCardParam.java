package com.center.medical.finance.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 体检卡管理-卡充值 分页参数
 */
@Data
public class RechargeCardParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -2909127335326880527L;

    @ApiModelProperty(value = "是否为增加：0.为充值 1.为消费")
    private Integer isAdd;

    @ApiModelProperty(value = "体检卡ID")
    private String cardId;

    @ApiModelProperty(value = "消费类型，消费类型：0.体检 1.药房 2.口腔科 3.眼镜店 4.合作 5.保健品")
    private Integer consumetype;
}
