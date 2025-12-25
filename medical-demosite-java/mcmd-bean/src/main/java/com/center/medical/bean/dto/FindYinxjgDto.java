package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FindYinxjgDto implements Serializable {
    private static final long serialVersionUID = 8957651684111079443L;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "描述")
    private String ms;

    @ApiModelProperty(value = "科室编码，对应原系统的deptId")
    private String deptNo;

    @ApiModelProperty(value = "id")
    private String id;
}
