package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改预约 参数
 */
@Data
public class ChangeDateParam implements Serializable {
    private static final long serialVersionUID = 3737989926550518899L;

    @ApiModelProperty(value = "医疗机构订单 ID")
    private String hospitalOrderId;

    @ApiModelProperty(value = "体检平台订单 ID")
    private String orderId;

    @ApiModelProperty(value = "加项 id(多个 id 英文逗号分割)")
    private String addItems;

    @ApiModelProperty(value = "预约时间")
    private String appointmentTime;

    @ApiModelProperty(value = "午别：1 或空：上午，2：下午注意此字段如果为空，请默认为上午")
    private String noonType;

    @ApiModelProperty(value = "是否 VIP 预约：否：N  是：Y")
    private String isVip;

    @ApiModelProperty(value = "appId")
    private String appId;

}
