package com.center.medical.bean.param;

import com.center.medical.bean.dto.GetTempFeeitemDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 科室加项 付款方式
 */
@Data
public class PaymentParam implements Serializable {
    private static final long serialVersionUID = 862965483841021517L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "合计价格")
    private Double TotalPrice;

    @ApiModelProperty(value = "付款方式ID")
    private String idPayway;

    @ApiModelProperty(value = "卡号(体检卡、会员卡等)/付款码(微信、支付宝付款码)")
    private String cardId;

    @ApiModelProperty(value = "交易方式，生成二维码时需要这个参数，W01 微信扫码支付，A01 支付宝扫码支付")
    private String paytype;

    @ApiModelProperty(value = "收费项目数据")
    private List<GetTempFeeitemDto> list;

    @ApiModelProperty(value = "通联平台的交易流水号")
    private String consumeId;

    @ApiModelProperty(value = "类型：9通联支付、11通联支付2")
    private Integer type;
}

