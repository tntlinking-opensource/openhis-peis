package com.center.medical.finance.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取销售经理 参数
 */
@Data
public class FeeChargerDataParam implements Serializable {
    private static final long serialVersionUID = 1363233325156305069L;

    @ApiModelProperty(value = "搜索条件")
    private String key;

    @ApiModelProperty(value = "支付方式id")
    private String way;

    @ApiModelProperty(value = "id")
    private String id;
}
