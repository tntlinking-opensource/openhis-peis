package com.center.medical.machine.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询科室信息
 */
@Data
public class FindDeptDto implements Serializable {
    private static final long serialVersionUID = -864761210038575870L;

    @ApiModelProperty(value = "科室编码，对应原系统的deptId")
    private String deptNo;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "科室报告排序")
    private Integer reportSort;

}
