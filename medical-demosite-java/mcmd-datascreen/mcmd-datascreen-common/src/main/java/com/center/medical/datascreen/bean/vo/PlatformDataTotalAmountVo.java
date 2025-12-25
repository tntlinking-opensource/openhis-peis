package com.center.medical.datascreen.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 总营业额
 * @author xhp
 * @since 2023-06-17 11:03
 */
@Data
public class PlatformDataTotalAmountVo {
    @ApiModelProperty(value = "总营业额")
    private double amount;
}
