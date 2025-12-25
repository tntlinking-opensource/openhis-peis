package com.center.medical.report.bean.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class BgGriddata implements Serializable {

    private static final long serialVersionUID = 6391542054303253613L;

    @ApiModelProperty(value = "状态,added添加，modified修改，removed删除")
    private String state;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "结论词ID")
    private String basconclusionId;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @ApiModelProperty(value = "总检建议")
    private String totalAdvice;

    @ApiModelProperty(value = "排序")
    private String sort;

    @ApiModelProperty(value = "合并结伦词ID")
    private String mergeId;

    @ApiModelProperty(value = "结论名称")
    private String mergeName;

    @ApiModelProperty(value = "结论词")
    private String basconclusion;

    @ApiModelProperty(value = "标志：0不出现,1出现")
    private Integer see;

    @ApiModelProperty(value = "团检建议")
    private String tjjy;

}
