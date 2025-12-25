package com.center.medical.datascreen.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xhp
 * @since 2023-06-13 8:53
 */
@Data
public class DatascreenBaseListVo {
    @ApiModelProperty(value = "name")
    private String name;
    @ApiModelProperty(value = "value")
    private String value;
}
