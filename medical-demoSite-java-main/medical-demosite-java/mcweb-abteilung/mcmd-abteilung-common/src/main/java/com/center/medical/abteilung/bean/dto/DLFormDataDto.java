package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  肺功能 审核 数据
 */
@Data
public class DLFormDataDto implements Serializable {

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "是否弃检(弃用)")
    private String isUnchecked;

    @ApiModelProperty(value = "是否危急值")
    private String isDanger;

    @ApiModelProperty(value = "用力肺活量(测定值)")
    private Double fvc;

    @ApiModelProperty(value = "用力肺活量（预测值）")
    private Double predictFvc;

    @ApiModelProperty(value = "用力肺活量（%预测值）")
    private Double percentageFvc;

    @ApiModelProperty(value = "fEV1")
    private String fEV1;

    @ApiModelProperty(value = "predictFEV1")
    private String predictFEV1;

    @ApiModelProperty(value = "percentageFEV1")
    private String percentageFEV1;

    @ApiModelProperty(value = "fEV1FVC")
    private String fEV1FVC;

    @ApiModelProperty(value = "predictFEV1FVC")
    private String predictFEV1FVC;

    @ApiModelProperty(value = "percentageFEV1FVC")
    private String percentageFEV1FVC;

    @ApiModelProperty(value = "最大呼气中期流速（MMEF）")
    private Double mmef;

    @ApiModelProperty(value = "最大呼气中期流速（MMEF 预测值）")
    private Double predictMmef;

    @ApiModelProperty(value = "最大呼气中期流速（MMEF %预测值）")
    private Double percentageMmef;

    @ApiModelProperty(value = "fEF25")
    private String fEF25;

    @ApiModelProperty(value = "predictFEF25")
    private String predictFEF25;

    @ApiModelProperty(value = "percentageFEF25")
    private String percentageFEF25;

    @ApiModelProperty(value = "fEF50")
    private String fEF50;

    @ApiModelProperty(value = "predictFEF50")
    private String predictFEF50;

    @ApiModelProperty(value = "percentageFEF50")
    private String percentageFEF50;

    @ApiModelProperty(value = "fEF75")
    private String fEF75;

    @ApiModelProperty(value = "predictFEF75")
    private String predictFEF75;

    @ApiModelProperty(value = "percentageFEF75")
    private String percentageFEF75;
}
