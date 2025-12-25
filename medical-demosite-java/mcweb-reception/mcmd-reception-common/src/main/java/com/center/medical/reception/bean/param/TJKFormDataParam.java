package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 体检卡 formdata 数据
 */
@Data
public class TJKFormDataParam implements Serializable {
    private static final long serialVersionUID = -111240036164753597L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "卡ID")
    private String cardId;

    @ApiModelProperty(value = "卡类型")
    private String cardType;

    @ApiModelProperty(value = "kyje")
    private String kyje;

    @ApiModelProperty(value = "卡标识")
    private String kbs;

    @ApiModelProperty(value = "有效期")
    private String yxq;

    @ApiModelProperty(value = "分中心ID")
    private String branchCenter;

    @ApiModelProperty(value = "卡说明")
    private String ksm;

    @ApiModelProperty(value = "卡备注")
    private String kbz;

    @ApiModelProperty(value = "体检号")
    private String chargeId;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "金额实付")
    private Double jsf;

    @ApiModelProperty(value = "记录增加或减少的金额或者积分：大于0增加、小于0减少")
    private Double limit;

    @ApiModelProperty(value = "消费类型ID")
    private String consumeType;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty(value = "备注")
    private String memotext;

    @ApiModelProperty(value = "体检号(ID)")
    private String patientcode;

    @ApiModelProperty(value = "支付方式ID")
    private String idPayway;

    @ApiModelProperty(value = "扣款方式：1.体检卡 2.会员卡积分 3.微信扫码枪 4.支付宝扫码枪 5.会员卡余额 6.家庭卡余额 7.家庭卡积分 8.微信扫码支付", position = 9, required = true)
    private Integer kkfs;
}
