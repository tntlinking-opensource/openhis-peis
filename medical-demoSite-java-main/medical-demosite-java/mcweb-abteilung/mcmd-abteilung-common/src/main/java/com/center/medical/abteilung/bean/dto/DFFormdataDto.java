package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 一般检查审核保存参数
 */
@Data
public class DFFormdataDto implements Serializable {
    private static final long serialVersionUID = -534938108564386100L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "是否弃检")
    private String isUnchecked;

    @ApiModelProperty(value = "是否危急值")
    private String isDanger;

    @ApiModelProperty(value = "收缩压")
    private Double ssy;

    @ApiModelProperty(value = "舒张压")
    private Double szy;

    @ApiModelProperty(value = "血压")
    private String xy;

    @ApiModelProperty(value = "脉搏")
    private Double mb;

    @ApiModelProperty(value = "身高")
    private String sg;

    @ApiModelProperty(value = "体重")
    private String tz;

    @ApiModelProperty(value = "体重指数")
    private Double bmi;

    @ApiModelProperty(value = "呼吸频率")
    private Double respiratoryRate;

    @ApiModelProperty(value = "体温测量")
    private Double temperature;

    @ApiModelProperty(value = "胸围")
    private Double bust;

    @ApiModelProperty(value = "营养状况")
    private String commonState;

    @ApiModelProperty(value = "血压文字描述")
    private String xyms;

    @ApiModelProperty(value = "体重文字描述")
    private String bmims;
}
