package com.center.medical.finance.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 老系统体检卡消费参数
 */
@Data
public class OldPaCardNoParam implements Serializable {
    private static final long serialVersionUID = 7662718699954670707L;

    @ApiModelProperty(value = "授权码")
    private String authCode;

    @ApiModelProperty(value = "会员卡号")
    private String patientcardno;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "收费员名称")
    private String userName;

    @ApiModelProperty(value = "备注")
    private String memotext;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "记录增加或减少的金额或者积分：大于0增加、小于0减少")
    private Double limit;

    @ApiModelProperty(value = "分中心ID")
    private String branchCenter;
}
