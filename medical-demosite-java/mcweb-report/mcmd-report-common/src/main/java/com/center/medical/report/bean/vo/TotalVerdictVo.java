package com.center.medical.report.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-12-08 9:40
 * @description:
 */
@Data
public class TotalVerdictVo implements Serializable {
    private static final long serialVersionUID = -851431071618400136L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "标识 0不出现,1出现 ")
    private String flag;

    @ApiModelProperty(value = "结论词ID")
    private String basconclusionId;

    @ApiModelProperty(value = "总检结论词名称 跟老系统的basconclusion是一个字段")
    private String basconclusionName;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @ApiModelProperty(value = "科室")
    private String division;

    @ApiModelProperty(value = "顺序")
    private Integer verdictSort;

    @ApiModelProperty(value = "合并结伦词ID")
    private String mergeId;

    @ApiModelProperty(value = "总检结伦词合并名称")
    private String mergeName;

    @ApiModelProperty(value = "总检建议")
    private String totalAdvice;

    @ApiModelProperty(value = "操作人")
    private String creater;

    @ApiModelProperty(value = "团检建议,跟老系统tjjy是一个字段")
    private String tjjy;


}
