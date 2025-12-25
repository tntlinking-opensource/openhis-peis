package com.center.medical.pacs.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 *  保存Griddata参数
 */
@Data
public class PCGriddataDto implements Serializable {
    private static final long serialVersionUID = -870884606787858804L;

    @ApiModelProperty(value = "pacs体征词id")
    private String tzcid;

    @ApiModelProperty(value = "体证词名称")
    private String tzcname;

}
