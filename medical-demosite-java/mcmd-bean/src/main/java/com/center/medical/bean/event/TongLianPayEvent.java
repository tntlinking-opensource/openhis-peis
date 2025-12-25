package com.center.medical.bean.event;

import com.center.medical.bean.param.TongLianPayParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 通联支付生成二维码
 */
@Data
@AllArgsConstructor
public class TongLianPayEvent {

    @ApiModelProperty(value = "参数")
    private TongLianPayParam param;
}
