package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 骨密度参数
 */
@Data
public class DmdGriddata implements Serializable {
    private static final long serialVersionUID = 3804217244296033773L;

    @ApiModelProperty(value = "结论词Id")
    private String jlcId;

    @ApiModelProperty(value = "检查项目id")
    private String jcxmId;

    @ApiModelProperty(value = "体征词id")
    private String tzcId;



}
