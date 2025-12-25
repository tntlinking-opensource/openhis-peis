package com.center.medical.datascreen.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分中心经纬度
 * @author xhp
 * @since 2023-06-27 9:56
 */
@Data
public class PlatformDataSelectBranchPositionVo {
    @ApiModelProperty(value = "分中心id")
    private String branchId;

    @ApiModelProperty(value = "分中心名字")
    private String fzx;

    @ApiModelProperty(value = "经度")
    private String lng;

    @ApiModelProperty(value = "纬度")
    private String lat;
}
