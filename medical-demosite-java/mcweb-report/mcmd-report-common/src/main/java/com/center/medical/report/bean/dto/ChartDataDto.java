package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 折线图数据DTO
 */
@Data
public class ChartDataDto implements Serializable {
    private static final long serialVersionUID = 7930795445232417674L;

    @ApiModelProperty(value = "lastTime")
    private String lastTime;

    @ApiModelProperty(value = "status9")
    private String status9;

    @ApiModelProperty(value = "status7")
    private String status7;
}
