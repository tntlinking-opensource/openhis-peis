package com.center.medical.member.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 新增家庭卡办理-左下数据
 */
@Data
public class ChargeInfoVo implements Serializable {
    private static final long serialVersionUID = 3754286129443841875L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "结算金额")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "外送备注")
    private String note;

    @ApiModelProperty(value = "支付方式名称")
    private String paywayName;

    @ApiModelProperty(value = "付款方式id")
    private String idPayway;

    @ApiModelProperty(value = "收费员")
    private String feecharger;

    @ApiModelProperty(value = "结算日期")
    private Date moneyamountpaiddate;
}
