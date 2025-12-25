package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RecalculatePriceDto implements Serializable {
    private static final long serialVersionUID = 3163540676442083783L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "套餐价格")
    private Double zhjg;

    @ApiModelProperty(value = "体检者价格合计")
    private Double factprices;
}
