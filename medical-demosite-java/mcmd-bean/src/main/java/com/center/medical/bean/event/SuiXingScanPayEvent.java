package com.center.medical.bean.event;

import com.center.medical.bean.param.SuiXingReverseScanParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 随行支付 被扫
 */
@Data
@AllArgsConstructor
public class SuiXingScanPayEvent {

    @ApiModelProperty(value = "参数")
    private SuiXingReverseScanParam param;


}
