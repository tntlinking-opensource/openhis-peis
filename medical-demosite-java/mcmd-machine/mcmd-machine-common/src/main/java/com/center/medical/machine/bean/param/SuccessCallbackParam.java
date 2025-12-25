package com.center.medical.machine.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 支付成功后的回调参数
 */
@Data
public class SuccessCallbackParam implements Serializable {

    @NotBlank(message = "请填写流水号!")
    @ApiModelProperty(value = "流水号")
    private String trxid;


    @NotBlank(message = "请填写id!")
    @ApiModelProperty(value = "返回列表的id")
    private String id;
}
