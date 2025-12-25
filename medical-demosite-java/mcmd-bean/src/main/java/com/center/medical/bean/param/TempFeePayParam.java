package com.center.medical.bean.param;

import com.center.medical.bean.dto.GetTempFeeitemDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 科室加项 支付参数
 */
@Data
public class TempFeePayParam implements Serializable {
    private static final long serialVersionUID = 6806815162937507156L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "合计价格")
    private Double TotalPrice;

    @ApiModelProperty(value = "收费项目数据")
    private List<GetTempFeeitemDto> list;

    @ApiModelProperty(value = "付款方式ID")
    private String idPayway;

    @ApiModelProperty(value = "支付方式名称")
    private String payway;

    @ApiModelProperty(value = "卡号(体检卡、会员卡等)/付款码(微信、支付宝付款码)")
    private String cardId;

    @ApiModelProperty(value = "通联平台的交易流水号,传这个流水号就直接跳过支付")
    private String consumeId;

    @ApiModelProperty(value = "收费记录id,通联返回2000时，支付成功时就需要传这个")
    private String chargeId;

    @ApiModelProperty(value = "交易方式，生成二维码时需要这个参数，W01 微信扫码支付，A01 支付宝扫码支付")
    private String paytype;
}
