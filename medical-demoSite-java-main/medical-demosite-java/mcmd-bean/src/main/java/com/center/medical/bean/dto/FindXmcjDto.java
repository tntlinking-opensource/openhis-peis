package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FindXmcjDto implements Serializable {
    private static final long serialVersionUID = 4871657293017253013L;

    @ApiModelProperty(value = "科室id")
    private String idKs;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "收费项目ID")
    private String idExamfeeitem;

    @ApiModelProperty(value = "收费项目打印名称")
    private String examfeeitemNameprn;

    @ApiModelProperty(value = "count1")
    private Integer count1;

    @ApiModelProperty(value = "count2")
    private Integer count2;

    @ApiModelProperty(value = "count3")
    private Integer count3;

    @ApiModelProperty(value = "count4")
    private Integer count4;

    @ApiModelProperty(value = "部门排序")
    private Integer reportSort;
}
