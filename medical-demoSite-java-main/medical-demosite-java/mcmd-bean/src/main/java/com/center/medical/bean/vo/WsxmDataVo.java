package com.center.medical.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class WsxmDataVo implements Serializable {
    private static final long serialVersionUID = -3442274103055675079L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "项目名称")
    private String xmmc;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "项目id")
    private String idItemName;

    @ApiModelProperty(value = "收费项目名称")
    private String itemName;

    @ApiModelProperty(value = "科室id")
    private String ksId;

    @ApiModelProperty(value = "科室名称")
    private String ksName;
}
