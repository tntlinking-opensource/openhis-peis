package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取小程序套餐参数
 */
@Data
public class GetMealListParam implements Serializable {
    private static final long serialVersionUID = -5581499298933137357L;


    @ApiParam(value = "每页大小，默认10", required = false, defaultValue = "10")
    private long size = 10;


    @ApiParam(value = "当前页，默认1", required = false, defaultValue = "0")
    private long current = 0;


    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "分中心id")
    private String fzxId;
}
