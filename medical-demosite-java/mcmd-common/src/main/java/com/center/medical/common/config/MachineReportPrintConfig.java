package com.center.medical.common.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MachineReportPrintConfig implements Serializable {


    @ApiModelProperty(value = "是否开启")
    private Boolean isOpen;


    @ApiModelProperty(value = "既往报告价格")
    private Double previousReportsPrice;


}
