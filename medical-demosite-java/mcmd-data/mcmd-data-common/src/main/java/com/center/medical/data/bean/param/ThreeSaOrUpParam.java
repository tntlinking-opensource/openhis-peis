package com.center.medical.data.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 第三方接口-保存或修改
 */
@Data
public class ThreeSaOrUpParam implements Serializable {
    private static final long serialVersionUID = -1458599768202320778L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "检查项目Id")
    private String baseItemId;

    @ApiModelProperty(value = "检查项目名称")
    private String baseItemName;

    @ApiModelProperty(value = "检查项目编号")
    private String baseItemCode;

    @ApiModelProperty(value = "第三方接口名称")
    private String interfaceName;

    @ApiModelProperty(value = "第三方检查项目编号")
    private String interfaceItemCode;
}
