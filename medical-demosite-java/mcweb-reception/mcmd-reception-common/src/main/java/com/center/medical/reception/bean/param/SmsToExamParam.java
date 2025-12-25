package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 来检短信提醒-保存参数
 */
@Data
public class SmsToExamParam implements Serializable {
    private static final long serialVersionUID = -1122966405089621429L;

    @ApiModelProperty(value = "订单ID")
    private String orderId;

    @ApiModelProperty(value = "体检者id集合")
    private List<String> ids;

    @ApiModelProperty(value = "sms短信联系人")
    private String idContact;

    @ApiModelProperty(value = "电话")
    private String phone;
}
