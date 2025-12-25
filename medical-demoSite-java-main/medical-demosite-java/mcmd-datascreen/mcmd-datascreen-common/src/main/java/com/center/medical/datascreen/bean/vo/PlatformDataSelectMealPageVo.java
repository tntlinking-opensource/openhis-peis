package com.center.medical.datascreen.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 体检套餐概况
 * @author xhp
 * @since 2023-06-02 14:13
 */
@Data
public class PlatformDataSelectMealPageVo {
    @ApiModelProperty(value = "套餐名称")
    private String examsuiteName;
    @ApiModelProperty(value = "销量")
    private int sales;
    @ApiModelProperty(value = "销售额")
    private String amount;
}
