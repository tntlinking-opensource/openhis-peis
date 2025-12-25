package com.center.medical.datascreen.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团检/个检体检人数
 * @author xhp
 * @since 2023-06-02 8:43
 */
@Data
public class PlatformDataNumberListVo {
    @ApiModelProperty(value = "中心名称")
    private String fzx;
    @ApiModelProperty(value = "人数")
    private int number;
    @ApiModelProperty(value = "销售额")
    private double amount;
}
