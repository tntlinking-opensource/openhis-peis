package com.center.medical.finance.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 老系统会员卡参数
 */
@Data
public class OldMemCardParam implements Serializable {
    private static final long serialVersionUID = -405998582041009882L;

    @ApiModelProperty(value = "授权码")
    private String authCode;

    @ApiModelProperty(value = "收费员名称")
    private String userName;

    @ApiModelProperty(value = "备注")
    private String memotext;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "体检号")
    private String chargeId;

    @ApiModelProperty(value = "卡ID")
    private String cardId;

    @ApiModelProperty(value = "记录增加或减少的金额或者积分：大于0增加、小于0减少")
    private Double limit;

    @ApiModelProperty(value = "消费类型：0.体检 1.药房 2.口腔科 3.眼镜店 4.合作 5.保健品 6.团体结算")
    private String consumeType;

    @ApiModelProperty(value = "分中心ID")
    private String branchCenter;
}
