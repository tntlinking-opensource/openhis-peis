package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 补检保存数据
 */
@Data
public class UIDDataDto implements Serializable {
    private static final long serialVersionUID = 3451803331171797419L;

    @ApiModelProperty(value = "类型,type:0->补检,type:1->迟检,type:2->弃检")
    private String type;

    @ApiModelProperty(value = "值,1反补检0补检")
    private String value;

}
