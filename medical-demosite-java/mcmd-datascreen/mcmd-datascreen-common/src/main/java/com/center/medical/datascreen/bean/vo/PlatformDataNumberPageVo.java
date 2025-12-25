package com.center.medical.datascreen.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分中心列表 分页
 * @author xhp
 * @since 2023-06-02 8:43
 */
@Data
public class PlatformDataNumberPageVo {
    @ApiModelProperty(value = "中心名称")
    private String fzx;
    @ApiModelProperty(value = "人数")
    private int number;
    @ApiModelProperty(value = "销售额")
    private double amount;
    @ApiModelProperty(value = "分中心id")
    private String branchId;
}
