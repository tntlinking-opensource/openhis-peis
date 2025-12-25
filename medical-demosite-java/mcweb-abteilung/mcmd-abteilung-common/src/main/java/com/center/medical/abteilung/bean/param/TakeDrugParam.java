package com.center.medical.abteilung.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 药房取药参数
 */
@Data
public class TakeDrugParam implements Serializable {
    private static final long serialVersionUID = -123139505739577872L;

    @ApiModelProperty(value = "是否已开药：0.否 1.已成交 2.未成交 3.退药")
    private Integer isFinished;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "价格")
    private Integer price;


}
