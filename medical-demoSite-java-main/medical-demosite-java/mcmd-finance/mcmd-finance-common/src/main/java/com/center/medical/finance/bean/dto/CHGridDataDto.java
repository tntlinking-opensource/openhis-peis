package com.center.medical.finance.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 体检卡管理-体检卡办理保存或修改 参数
 */
@Data
public class CHGridDataDto implements Serializable {

    @ApiModelProperty(value = "实付金额")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "是否统收：0:不是统收1：统收")
    private Integer isTotalcharge;

    @ApiModelProperty(value = "已收费：1已收")
    private Integer isCharged;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "id")
    private String uid;

    @ApiModelProperty(value = "操作标识：{removed:移除，modified:修改，added：新增}")
    private String state;

    @ApiModelProperty(value = "付款方式ID")
    private String idPayway;

    @ApiModelProperty(value = "卡号")
    private String cardno;

    @ApiModelProperty(value = "备注")
    private String note;

}
