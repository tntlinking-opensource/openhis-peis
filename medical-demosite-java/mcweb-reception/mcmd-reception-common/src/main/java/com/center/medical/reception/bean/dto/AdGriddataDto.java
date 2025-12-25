package com.center.medical.reception.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 预付方式保存表格数据
 */
@Data
public class AdGriddataDto implements Serializable {
    private static final long serialVersionUID = 1856951409971708514L;

    @ApiModelProperty(value = "金额实付")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "是否统收：0:不是统收1：统收")
    private Integer isTotalcharge;

    @ApiModelProperty(value = "已收费")
    private Integer fFeecharged;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "顺序")
    private String uid;

    @ApiModelProperty(value = "操作标识：{removed:移除，modified:修改，added：新增}")
    private String state;

    @ApiModelProperty(value = "支付方式")
    private String payway;

    @ApiModelProperty(value = "支付方式ID")
    private String idPayway;

    @ApiModelProperty(value = "卡号")
    private String cardno;


}
