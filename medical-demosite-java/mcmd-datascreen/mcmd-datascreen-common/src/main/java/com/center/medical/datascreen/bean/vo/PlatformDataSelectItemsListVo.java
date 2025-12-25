package com.center.medical.datascreen.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动产品销售(项目)
 * @author xhp
 * @since 2023-06-05 15:34
 */
@Data
public class PlatformDataSelectItemsListVo {
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "数量")
    private int number;
    @ApiModelProperty(value = "总价")
    private double amount;
}
