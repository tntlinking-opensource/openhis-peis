package com.center.medical.datascreen.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 中心分区
 * @author xhp
 * @since 2023-06-06 14:13
 */
@Data
public class BranchDataBranchVo {
    @ApiModelProperty(value = "分中心id")
    private String branchId;

    @ApiModelProperty(value = "分中心名字")
    private String fzx;
}
