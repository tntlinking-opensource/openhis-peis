package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 更改收费方式 参数
 */
@Data
public class ChangePaymentMethodParam implements Serializable {
    private static final long serialVersionUID = 3442738035388349910L;

    @NotEmpty(message = "id不能为空!")
    @ApiModelProperty(value = "id")
    private String id;

    @NotEmpty(message = "支付方式id不能为空!")
    @ApiModelProperty(value = "支付方式id")
    private String idPayway;

}
