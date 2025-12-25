package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 单位预约查询单位参数
 */
@Data
public class QueryUnitParam implements Serializable {
    private static final long serialVersionUID = -4528459358080400453L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "每页显示条数")
    private long size;

    @ApiModelProperty(value = "当前页")
    private long current;

}
