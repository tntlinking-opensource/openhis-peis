package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询各科室小结
 */
@Data
public class DepResult1Dto implements Serializable {
    private static final long serialVersionUID = 7543951277263142609L;


    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "peic")
    private String peiC;

    @ApiModelProperty(value = "pei1c")
    private String pei1C;

}
