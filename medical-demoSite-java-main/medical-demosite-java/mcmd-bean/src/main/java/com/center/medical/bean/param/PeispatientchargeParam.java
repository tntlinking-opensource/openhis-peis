package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞船长
 * @date: 2023/4/12 17:48
 * @description: 支付记录参数
 */
@Data
public class PeispatientchargeParam implements Serializable {
    private static final long serialVersionUID = 8528661423511634267L;

    @ApiModelProperty(value = "金额应付", position = 0, required = true)
    private Double moneyamount;

    @ApiModelProperty(value = "金额实付", position = 1, required = true)
    private Double moneyamountpaid;

    @ApiModelProperty(value = "是否统收：0:不是统收1：统收", position = 2, required = true)
    private Integer isTotalcharge;

    @ApiModelProperty(value = "已收费：0.未收费 1.已收费", position = 3, required = true)
    private Integer fFeecharged;

    @ApiModelProperty(value = "支付方式ID", position = 5, required = true)
    private String idPayway;

    @ApiModelProperty(value = "是否可以编辑卡号：0.可以 1.不可以", position = 6, required = true)
    private Integer isChange;

    @ApiModelProperty(value = "备注", position = 8)
    private String note;

    @ApiModelProperty(value = "操作标识：{removed:移除，modified:修改，added：新增}", position = 7, required = true)
    private String _state;

    @ApiModelProperty(value = "卡号", position = 8)
    private String cardno;

    @ApiModelProperty(value = "是否卡号收款：0.否 1.是", position = 9)
    private Integer kashou;

    @ApiModelProperty(value = "_id", position = 10)
    private Integer _id;

    @ApiModelProperty(value = "_uid", position = 11)
    private Integer _uid;

    @ApiModelProperty(value = "是否是加项收费   1 是  null不是")
    private Integer isAdd;
}
