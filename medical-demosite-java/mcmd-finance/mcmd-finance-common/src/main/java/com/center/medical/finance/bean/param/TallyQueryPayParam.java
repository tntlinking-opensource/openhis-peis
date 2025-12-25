package com.center.medical.finance.bean.param;

import com.center.medical.bean.enums.Kkfs;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 记账管理-记账结算分页参数
 */
@Data
public class TallyQueryPayParam implements Serializable {
    private static final long serialVersionUID = 7061843381299521481L;


    @ApiModelProperty(value = "体检号", position = 0, required = true)
    private String patientcode;

    @ApiModelProperty(value = "体检者名称")
    private String patientname;

    @ApiModelProperty(value = "支付方式ID", position = 3, required = true)
    private String idPayway;

    @ApiModelProperty(value = "扣款方式是3或4，须传扫码设备号(微信支付使用)", position = 9)
    private String deviceInfo;

    @ApiModelProperty(value = "卡号(体检卡、会员卡等)/付款码(微信、支付宝付款码)", position = 4, required = true)
    private String cardId;

    @ApiModelProperty(value = "消费金额", position = 6, required = true)
    private Double limit;

    /**
     * @see Kkfs
     */
    @ApiModelProperty(value = "扣款方式：1.体检卡 2.会员卡积分 3.微信扫码枪 4.支付宝扫码枪 5.会员卡余额 6.家庭卡余额 7.家庭卡积分 8.微信扫码支付 9.通联支付", position = 9, required = true)
    private Integer kkfs;

    /**
     * 支付流水ID：卡扣款则是卡操作记录ID，微信或者支付宝在线支付则是第三方支付流水号
     */
    @ApiModelProperty(value = "通联平台的交易流水号,传这个流水号就直接跳过支付")
    private String consumeId;


    @ApiModelProperty(value = "收费记录id,通联返回2000时，支付成功时就需要传这个")
    private String chargeId;


    @ApiModelProperty(value = "备注(卡支付使用)")
    private String memotext;


    @ApiModelProperty(value = "分中心ID(卡支付使用)")
    private String branchId;


    @ApiModelProperty(value = "消费类型ID(卡支付使用)", position = 9, required = true)
    private String consumeType;



}
