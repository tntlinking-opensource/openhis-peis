package com.center.medical.datascreen.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 时间段+分中心id
 * @author xhp
 * @since 2023-06-02 9:37
 */
@Data
public class DatascreenBaseTimeAndBranchParam extends PlatformDataBaseParam{
    @ApiModelProperty(value = "分中心id",required = true)
    @NotEmpty
    private String branchId;
}
