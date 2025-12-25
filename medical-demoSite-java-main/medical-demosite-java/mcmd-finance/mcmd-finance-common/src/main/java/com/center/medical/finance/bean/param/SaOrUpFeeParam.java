package com.center.medical.finance.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检卡消费参数
 */
@Data
public class SaOrUpFeeParam implements Serializable {
    private static final long serialVersionUID = 7192221556158932186L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "卡号")
    private String cardId;

    @ApiModelProperty(value = "卡类型")
    private String cardType;

    @ApiModelProperty(value = "kyje")
    private Double kyje;

    @ApiModelProperty(value = "卡标识")
    private String kbs;

    @ApiModelProperty(value = "有效期")
    private Date yxq;

    @ApiModelProperty(value = "分中心ID")
    private String branchCenter;

    @ApiModelProperty(value = "卡说明")
    private String ksm;

    @ApiModelProperty(value = "卡备注")
    private String kbz;

    @ApiModelProperty(value = "收费项目ID(多个 逗号分隔)")
    private String chargeId;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "金额实付")
    private Double jsf;

    @ApiModelProperty(value = "记录增加或减少的金额或者积分")
    private Double limit;

    @ApiModelProperty(value = "消费类型，消费类型：0.体检 1.药房 2.口腔科 3.眼镜店 4.合作 5.保健品")
    private String consumeType;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "备注")
    private String memotext;
}
