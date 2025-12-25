package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询结论数量
 */
@Data
public class SerialnoCountDto implements Serializable {
    private static final long serialVersionUID = -8479496546556394997L;

    @ApiModelProperty(value = "count1")
    private String count1;

    @ApiModelProperty(value = "count2")
    private String count2;

    @ApiModelProperty(value = "count3")
    private String count3;

    @ApiModelProperty(value = "count4")
    private String count4;

    @ApiModelProperty(value = "count5")
    private String count5;

    @ApiModelProperty(value = "count")
    private String count;
}
