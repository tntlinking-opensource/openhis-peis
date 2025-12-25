package com.center.medical.bean.event;

import com.center.medical.bean.param.MicropayParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 微信线上支付
 */
@Data
@AllArgsConstructor
public class WxPayMicropayEvent {

    @ApiModelProperty(value = "参数")
    private MicropayParam micropayParam;
}
