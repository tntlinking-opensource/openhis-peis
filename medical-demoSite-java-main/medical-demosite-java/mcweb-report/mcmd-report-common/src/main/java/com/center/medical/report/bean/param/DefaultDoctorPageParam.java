package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页参数
 */
@Data
public class DefaultDoctorPageParam implements Serializable {
    private static final long serialVersionUID = -6414256637417129020L;

    @ApiModelProperty(value = "科室名称")
    private String deptName;

    @ApiModelProperty(value = "用户名称")
    private String userName;

}
