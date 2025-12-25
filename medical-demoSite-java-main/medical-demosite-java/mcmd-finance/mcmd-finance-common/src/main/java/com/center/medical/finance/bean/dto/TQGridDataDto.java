package com.center.medical.finance.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 记账管理-记账结算保存 下方结算数据
 */
@Data
public class TQGridDataDto implements Serializable {
    private static final long serialVersionUID = 3713234563832150860L;

    @ApiModelProperty(value = "结算金额")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "是否统收：0:不是统收 1：统收")
    private Integer isTotalcharge;

    @ApiModelProperty(value = "已收费")
    private Integer isCharged;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "uid")
    private String uid;

    @ApiModelProperty(value = "状态 added添加，modified修改，removed删除")
    private String state;

    @ApiModelProperty(value = "付款方式id")
    private String idPayway;

    @ApiModelProperty(value = "体检卡卡号")
    private String cardno;

    @ApiModelProperty(value = "备注")
    private String note;
}
