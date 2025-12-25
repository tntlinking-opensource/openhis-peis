package com.center.medical.datascreen.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动产品销售（套餐）
 * @author xhp
 * @since 2023-06-02 14:13
 */
@Data
public class PlatformDataSelectMealListVo {
    @ApiModelProperty(value = "套餐名称")
    private String examsuiteName;
    @ApiModelProperty(value = "销量")
    private int sales;
}
