package com.center.medical.enterprise.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 科室结果 返回数据
 */
@Data
public class KsResultsDto implements Serializable {
    private static final long serialVersionUID = -4522467873212804736L;

    @ApiModelProperty(value = "部门id")
    private String deptNo;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "检查结果总结")
    private String conclusions;
}
