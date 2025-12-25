package com.center.medical.center.qingdao.profession.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 体检项目结果
 */
@Data
public class OhItemResultDto implements Serializable {
    private static final long serialVersionUID = -92852756710408329L;

    @ApiModelProperty(value = "项目代码")
    private String itemCode;

    @ApiModelProperty(value = "检查（所见）结果")
    private String result;

    @ApiModelProperty(value = "符号代码")
    private String dataVersion;

    @ApiModelProperty(value = "是否异常")
    private String resultFlag;

    @ApiModelProperty(value = "参考上限")
    private String upperLimit;

    @ApiModelProperty(value = "参考下限")
    private String lowerLimit;

    @ApiModelProperty(value = "计量单位")
    private String unit;

    @ApiModelProperty(value = "胸片检查结果代码")
    private String radiographResult;
}
