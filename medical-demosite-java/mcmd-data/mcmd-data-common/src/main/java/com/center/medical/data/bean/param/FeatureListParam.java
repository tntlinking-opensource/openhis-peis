package com.center.medical.data.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 根据检查项目ID获取相对应的体征词参数
 */
@Data
public class FeatureListParam implements Serializable {
    private static final long serialVersionUID = -5213889396721712982L;

    @ApiModelProperty(value = "检查项目ID")
    private String id;

    @ApiModelProperty(value = "体征词名称")
    private String tzcname;

    @ApiModelProperty(value = "结论词")
    private String resultId;
}
