package com.center.medical.data.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取防护用品
 */
@Data
public class ProtectiveEquipmentVo implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "防护用品种类")
    private String defendIndividual;

    @ApiModelProperty(value = "输入码")
    private String inputCode;
}
