package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞船长
 * @date: 2023/5/26 15:05
 * @description: 复查额度支付参数
 */
@Data
public class ChargeRecheckParam implements Serializable {
    private static final long serialVersionUID = -5634623416091649820L;

    @ApiModelProperty(value = "体检号", position = 0, required = true)
    private String patientcode;

    @ApiModelProperty(value = "体检者名称", position = 1, required = true)
    private String patientname;

    @ApiModelProperty(value = "版本号", position = 2, required = true)
    private Long version;

    @ApiModelProperty(value = "支付方式ID", position = 3, required = true)
    private String idPayway;

    @ApiModelProperty(value = "卡号", position = 4, required = true)
    private String cardId;

    @ApiModelProperty(value = "结算费", position = 5, required = true)
    private Double jsf;

    @ApiModelProperty(value = "消费金额", position = 6, required = true)
    private Double limit;

    @ApiModelProperty(value = "分中心ID", position = 8, required = true)
    private String branchId;

    @ApiModelProperty(value = "收费员名称", position = 10)
    private String userName;

    @ApiModelProperty(value = "备注", position = 11)
    private String memotext;

    @ApiModelProperty(value = "支付记录信息", position = 7, required = true)
    private PeispatientchargeParam chargeParam;

    /**
     * 支付流水ID：卡扣款则是卡操作记录ID，微信或者支付宝在线支付则是第三方支付流水号
     */
    @ApiParam(hidden = true)
    private String consumeId;
}
