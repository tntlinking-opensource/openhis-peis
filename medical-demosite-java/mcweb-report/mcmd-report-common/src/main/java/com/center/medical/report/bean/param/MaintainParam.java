package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class MaintainParam implements Serializable {

    private static final long serialVersionUID = -2401006378657305564L;

    @ApiModelProperty(value = "是否合并，1是")
    private String isMerge;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "zjjy")
    private String zjjy;

    @ApiModelProperty(value = "团检建议")
    private String tjjy;

    @ApiModelProperty(value = "科室")
    private String ks;

    @ApiModelProperty(value = "结论词id，多个以逗号分割")
    private String conIds;

}
