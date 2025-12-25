package com.center.medical.machine.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 打印导引单参数
 */
@Data
public class ModelParam implements Serializable {
    private static final long serialVersionUID = 1970900011771178597L;

    @ApiModelProperty(value = "体检号集合")
    private List<String> ids;

    @ApiModelProperty(value = "模型")
    private String model;

    @ApiModelProperty(value = "分中心id")
    private String cid;

    @ApiModelProperty(value = "导引单样式")
    private String dydStyle;

    @ApiModelProperty(value = "用户打印名称")
    private String usePrintName;

}
