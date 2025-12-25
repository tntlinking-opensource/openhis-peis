package com.center.medical.bean.param;

import com.center.medical.bean.enums.Kkfs;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞船长
 * @date: 2023/4/12 17:06
 * @description: 退款参数（会员卡、体检卡、微信扫码、支付宝扫码等）
 */
@Data
@ApiModel(value = "退款参数（会员卡、体检卡、微信扫码、支付宝扫码等）")
public class RefundParam implements Serializable {
    private static final long serialVersionUID = 6625977846086257989L;

    @ApiModelProperty(value = "支付记录ID", position = 1, required = true)
    private String id;

    @ApiModelProperty(value = "版本号", position = 2, required = true)
    private Long version;

    @ApiModelProperty(value = "体检者名称", position = 3, required = true)
    private String patientname;

    @ApiModelProperty(value = "卡号(体检卡、会员卡等)/付款码(微信、支付宝付款码)", position = 4, required = true)
    private String cardId;

    @ApiModelProperty(value = "结算费", position = 5, required = true)
    private Double jsf;

    @ApiModelProperty(value = "消费金额", position = 6, required = true)
    private Double limit;

    @ApiModelProperty(value = "分中心ID", position = 8, required = true)
    private String branchId;

    @ApiModelProperty(value = "消费类型ID", position = 9, required = true)
    private String consumeType;

    /**
     * @see Kkfs
     */
    @ApiModelProperty(value = "扣款方式：1.体检卡 2.会员卡积分 3.微信扫码枪 4.支付宝扫码枪 5.会员卡余额 6.家庭卡余额 7.家庭卡积分 9通联支付 10随行支付", position = 9, required = true)
    private Integer kkfs;

    @ApiModelProperty(value = "扣款方式是3或4，须传扫码设备号", position = 9)
    private String deviceInfo;

    @ApiModelProperty(value = "收费员名称", position = 10)
    private String userName;

    @ApiModelProperty(value = "备注", position = 11)
    private String memotext;

    @ApiModelProperty(value = "是否预收 0否1是", position = 12)
    private Integer sfys;


    @ApiModelProperty(value = "身份证号（家庭卡使用）", position = 13)
    private String idcardno;

    @ApiModelProperty(value = "体检号（家庭卡使用）", position = 14)
    private String patientcode;

    @ApiModelProperty(value = "付款方式id（家庭卡使用）", position = 15)
    private String idPayway;

}
