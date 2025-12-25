package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 对比报告 查询三个体检号的各科室小结
 */
@Data
public class DepResult2Dto implements Serializable {
    private static final long serialVersionUID = 2442945005512530516L;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "pei_c")
    private String peiC;

    @ApiModelProperty(value = "pei1_c")
    private String pei1C;

    @ApiModelProperty(value = "pei2_c")
    private String pei2C;

}
