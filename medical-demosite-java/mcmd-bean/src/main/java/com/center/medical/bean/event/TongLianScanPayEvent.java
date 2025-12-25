package com.center.medical.bean.event;

import com.center.medical.bean.param.TongLianScanPayParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 通联扫码支付
 */
@Data
@AllArgsConstructor
public class TongLianScanPayEvent {

    @ApiModelProperty(value = "参数")
    private TongLianScanPayParam param;


}
